package com.chaoxi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.MinioUtils;
import com.chaoxi.common.R;
import com.chaoxi.config.MinioConfig;
import com.chaoxi.pojo.Resource;
import com.chaoxi.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private MinioUtils minioUtils;
    @Autowired
    private MinioConfig minioConfig;
    @Autowired
    private ResourceService resourceService;

    // ========== 接口1：初始化上传 ==========
    @PostMapping("/initUpload")
    public R initUpload(@RequestBody Resource resource) {
        // 1. 根据MD5查询是否已有相同文件（秒传）
        LambdaQueryWrapper<Resource> qw = new LambdaQueryWrapper<>();
        qw.eq(Resource::getFileMd5, resource.getFileMd5());
        qw.eq(Resource::getUploadStatus, 1);
        Resource existResource = resourceService.getOne(qw);
        if (existResource != null) {
            // 秒传：文件已存在，直接返回已有资源信息
            return R.success(existResource).add("isInstant", true);
        }

        // 2. 查询是否有上传中但未完成的记录（断点续传）
        qw = new LambdaQueryWrapper<>();
        qw.eq(Resource::getFileMd5, resource.getFileMd5());
        qw.eq(Resource::getUploadStatus, 0);
        Resource uploadingResource = resourceService.getOne(qw);
        if (uploadingResource != null) {
            // 断点续传：返回已上传的分片索引
            return R.success(uploadingResource).add("isResume", true);
        }

        // 3. 全新上传：创建资源记录
        resource.setUploadStatus(0);
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateTime(LocalDateTime.now());
        resource.setObjectName("resources/" + resource.getFileMd5() + "/" + resource.getFileName());
        resourceService.save(resource);
        return R.success(resource).add("isInstant", false).add("isResume", false);
    }

    // ========== 接口2：上传分片 ==========
    @PostMapping("/uploadChunk")
    public R uploadChunk(
            @RequestParam("file") MultipartFile file,
            @RequestParam("md5") String md5,
            @RequestParam("chunkIndex") Integer chunkIndex) {
        try {
            String chunkObjectName = "chunks/" + md5 + "/" + chunkIndex;
            InputStream inputStream = file.getInputStream();
            minioUtils.uploadChunk(minioConfig.getBucketName(), chunkObjectName, inputStream, file.getSize());

            // 更新已上传分片记录
            LambdaQueryWrapper<Resource> qw = new LambdaQueryWrapper<>();
            qw.eq(Resource::getFileMd5, md5);
            qw.eq(Resource::getUploadStatus, 0);
            Resource resource = resourceService.getOne(qw);
            if (resource != null) {
                String uploaded = resource.getChunkUploaded();
                if (StringUtils.isEmpty(uploaded)) {
                    resource.setChunkUploaded(String.valueOf(chunkIndex));
                } else {
                    List<String> list = Arrays.asList(uploaded.split(","));
                    if (!list.contains(String.valueOf(chunkIndex))) {
                        resource.setChunkUploaded(uploaded + "," + chunkIndex);
                    }
                }
                resource.setUpdateTime(LocalDateTime.now());
                resourceService.updateById(resource);
            }
            return R.success("分片上传成功");
        } catch (Exception e) {
            log.error("分片上传失败", e);
            return R.error("分片上传失败");
        }
    }

    // ========== 接口3：检查分片是否已上传（断点续传核心） ==========
    // sfhjf
    @GetMapping("/checkChunk")
    public R checkChunk(@RequestParam("md5") String md5) {
        LambdaQueryWrapper<Resource> qw = new LambdaQueryWrapper<>();
        qw.eq(Resource::getFileMd5, md5);
        qw.eq(Resource::getUploadStatus, 0);
        Resource resource = resourceService.getOne(qw);
        if (resource != null && !StringUtils.isEmpty(resource.getChunkUploaded())) {
            List<Integer> uploadedIndices = Arrays.stream(resource.getChunkUploaded().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            return R.success(uploadedIndices);
        }
        return R.success(Arrays.asList());
    }
    
    // ========== 接口4：合并分片 ==========

    
    @PostMapping("/merge")
    public R merge(@RequestParam("md5") String md5) {
        LambdaQueryWrapper<Resource> qw = new LambdaQueryWrapper<>();
        qw.eq(Resource::getFileMd5, md5);
        qw.eq(Resource::getUploadStatus, 0);
        Resource resource = resourceService.getOne(qw);
        if (resource == null) {
            return R.error("未找到上传记录");
        }

        try {
            String chunkPrefix = "chunks/" + md5;
            minioUtils.mergeChunks(
                minioConfig.getBucketName(),
                chunkPrefix,
                resource.getChunkCount(),
                resource.getObjectName()
            );

            // 清理临时分片
            minioUtils.cleanChunks(minioConfig.getBucketName(), chunkPrefix, resource.getChunkCount());

            // 更新状态为已完成
            resource.setUploadStatus(1);
            resource.setUpdateTime(LocalDateTime.now());
            resourceService.updateById(resource);

            return R.success("合并成功");
        } catch (Exception e) {
            log.error("合并分片失败", e);
            resource.setUploadStatus(2);
            resource.setUpdateTime(LocalDateTime.now());
            resourceService.updateById(resource);
            return R.error("合并失败");
        }
    }

    // ========== 接口5：分页查询资源列表 ==========
    @GetMapping("/page")
    public R page(Integer page, Integer pageSize, String name) {
        Page<Resource> resourcePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Resource> qw = new LambdaQueryWrapper<>();
        qw.like(!StringUtils.isEmpty(name), Resource::getFileName, name);
        qw.orderByDesc(Resource::getCreateTime);
        resourceService.page(resourcePage, qw);
        return R.success(resourcePage);
    }

    // ========== 接口6：删除资源 ==========
    @DeleteMapping
    public R deleteById(@RequestParam Long id) {
        Resource resource = resourceService.getById(id);
        if (resource != null) {
            try {
                minioUtils.removeFile(minioConfig.getBucketName(), resource.getObjectName());
            } catch (Exception e) {
                log.warn("MinIO文件删除失败: {}", resource.getObjectName());
            }
        }
        boolean b = resourceService.removeById(id);
        return b ? R.success("删除成功") : R.error("删除失败");
    }
}