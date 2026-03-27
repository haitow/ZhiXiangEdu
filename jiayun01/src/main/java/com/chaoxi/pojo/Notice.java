package com.chaoxi.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("notice")
public class Notice implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private String title;
  private String description;

  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
  @TableField(fill = FieldFill.INSERT)
  private Long createUser;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updateUser;
}
