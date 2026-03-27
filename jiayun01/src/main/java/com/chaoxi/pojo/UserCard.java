package com.chaoxi.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("user_card")
public class UserCard implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long userId;
  private Long cardId;
  private String cardName;
  private String tags;
  private String type;
  private Integer count;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Integer status;
  private String image;
  private BigDecimal price;
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

  @TableField(exist = false)
  private boolean disabled;
}
