package com.chaoxi.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoxi.pojo.Orders;
import com.github.pagehelper.PageInfo;

public interface OrdersService extends IService<Orders> {

  PageInfo<Orders> getAll(Integer page, Integer pageSize, Integer status);

}
