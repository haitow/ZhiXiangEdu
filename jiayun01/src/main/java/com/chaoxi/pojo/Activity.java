package com.chaoxi.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("activity")
public class Activity implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private String activityName;
  private double oldPrice;
  private double nowPrice;
  private int status;
  private Date endTime;
  private String image;
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
