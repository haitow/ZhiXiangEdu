package com.chaoxi.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("resource")
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String fileName;
    private String fileMd5;
    private Long fileSize;
    private Integer chunkSize;
    private Integer chunkCount;
    private String chunkUploaded;
    private String objectName;
    private String fileType;
    private Integer uploadStatus;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}