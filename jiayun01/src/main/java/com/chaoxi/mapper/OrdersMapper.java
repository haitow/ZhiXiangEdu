package com.chaoxi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaoxi.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
  List<Orders> getAll(@Param("status") Integer status);

}
