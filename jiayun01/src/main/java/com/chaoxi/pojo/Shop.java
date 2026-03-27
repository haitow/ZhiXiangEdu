package com.chaoxi.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("shop")
public class Shop implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private String shopName;
  private String image;
  private double oldPrice;
  private double newPrice;
  private int status;
  private int inventory;
  private String description;
  private Long categoryId;
  private int sort;
}
