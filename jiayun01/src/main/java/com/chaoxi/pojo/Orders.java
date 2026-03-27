package com.chaoxi.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Orders implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long userId;
  private Long shopId;
  private int shopCount;
  private double account;
  private Integer status;
  private String phone;
  public String image;
  private String shopName;
  private double price;
  private Long orderId;

  @TableField(exist = false)
  private OrderDetail orderDetail;
}
