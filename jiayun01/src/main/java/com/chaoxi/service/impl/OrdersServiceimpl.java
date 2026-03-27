package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.OrdersMapper;
import com.chaoxi.pojo.Orders;
import com.chaoxi.service.OrdersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceimpl
    extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService {

  @Autowired
  private OrdersMapper ordersMapper;

  @Override
  public PageInfo<Orders> getAll(Integer page, Integer pageSize, Integer status) {
    PageHelper.startPage(page,pageSize);
    List<Orders> ordersList = ordersMapper.getAll(status);
    PageInfo<Orders> ordersPageInfo = new PageInfo<>(ordersList);
    return ordersPageInfo;
  }
}
