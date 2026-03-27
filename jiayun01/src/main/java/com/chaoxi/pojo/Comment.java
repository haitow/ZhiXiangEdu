package com.chaoxi.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Comment implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long userId;
  private Long shopId;
  private String content;
  private int storage;

  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
  @TableField(fill = FieldFill.INSERT)
  private Long createUser;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updateUser;

  @TableField(exist = false)
  private Shop shop;
  @TableField(exist = false)
  private User user;
}
