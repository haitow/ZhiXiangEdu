package com.chaoxi.controller;


import com.chaoxi.common.MinioUtils;
import com.chaoxi.common.R;
import com.chaoxi.config.MinioConfig;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("common")
@Slf4j
public class CommonController {
  @Autowired
  private MinioUtils minioUtils;
  @Autowired
  private MinioConfig minioConfig;
  /**
   * 文件上传
   *
   * @param file
   */
  @PostMapping("/upload")
  public R<String> upload(@RequestParam("file") MultipartFile file) {
    try {
      //文件名
      String fileName = file.getOriginalFilename();
      String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
      //类型
      String contentType = file.getContentType();
      minioUtils.uploadFile(minioConfig.getBucketName(), file, newFileName, contentType);
      return R.success(newFileName) ;
    } catch (Exception e) {
      e.printStackTrace();
      return R.error("上传失败") ;
    }
  }
  /**
   * 文件下载
   *
   * @param fileName
   * @param response
   */
  @GetMapping("/download")
  public void download(@RequestParam("name") String fileName, HttpServletResponse response) {
    try {
      InputStream fileInputStream = minioUtils.getObject(
          minioConfig.getBucketName(), fileName);
      response.setHeader("Content-Disposition",
          "attachment;filename=" + fileName);
      response.setContentType("application/force-download");
      response.setCharacterEncoding("UTF-8");
      IOUtils.copy(fileInputStream, response.getOutputStream());
    } catch (Exception e) {
      log.error("下载失败");
    }
  }

}
