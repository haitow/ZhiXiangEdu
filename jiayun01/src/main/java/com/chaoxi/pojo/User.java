package com.chaoxi.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User implements Serializable{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String phone;
  private int sex;
  private LocalDate birthday;
  private String idNumber;
  private String image;
  private int status;
  private String type;
  private int isDeleted;

  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
  @TableField(fill = FieldFill.INSERT)
  private Long createUser;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updateUser;
}
