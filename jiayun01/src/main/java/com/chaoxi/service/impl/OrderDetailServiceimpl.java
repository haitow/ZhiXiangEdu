package com.chaoxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.OrderDetailMapper;
import com.chaoxi.pojo.OrderDetail;
import com.chaoxi.service.OrderDatailService;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailServiceimpl
    extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDatailService {
}
