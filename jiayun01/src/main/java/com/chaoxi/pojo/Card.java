package com.chaoxi.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("card")
public class Card implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  /** 种类  期限卡1 次卡2*/
  private String type;
  /** 价格*/
  private BigDecimal price;
  /** 之前价格*/
  private BigDecimal oldPrice;
  /** 次卡次数*/
  private Integer count;
  /** 期限卡和次卡时效*/
  private Integer day;
  /** 卡的上课类型*/
  private String tags;
  /** 图片*/
  private String image;
  /** 描述信息*/
  private String description;
  /** 状态 0 1*/
  private Integer status;
  /** 顺序*/
  private Integer sort;
  /** 创建时间*/
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /** 最后修改时间*/
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
  /** 创建人 */
  @TableField(fill = FieldFill.INSERT)
  private Long createUser;
  /** 修改人 */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updateUser;
  /** 是否停用 */
  private Integer isDeleted;
}
