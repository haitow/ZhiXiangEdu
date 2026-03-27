package com.chaoxi.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("user_class")
public class UserClass implements Serializable {
  private Long id;
  private Long userId;
  private Long classId;
  private String userName;
  private String className;
  private int status;
  private String tags;
  //创建时间
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
  //更新时间
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
  //创建人
  @TableField(fill = FieldFill.INSERT)
  private Long createUser;
  //修改人
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updateUser;
  private Integer isDeleted;
}
