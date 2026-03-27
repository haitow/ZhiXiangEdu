package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.OrderDetail;
import com.chaoxi.pojo.Orders;
import com.chaoxi.pojo.Shop;
import com.chaoxi.pojo.User;
import com.chaoxi.service.OrderDatailService;
import com.chaoxi.service.OrdersService;
import com.chaoxi.service.ShopService;
import com.chaoxi.service.UserService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {
  @Autowired
  private OrdersService ordersService;
  @Autowired
  private UserService userService;
  @Autowired
  private ShopService shopService;
  @Autowired
  private OrderDatailService orderDatailService;

//  分页查询
  @GetMapping("/page")
    public R page(@RequestParam Integer page,
                  @RequestParam Integer pageSize,
                  @RequestParam(required = false) Integer status){
    PageInfo<Orders> ordersPageInfo = ordersService.getAll(page, pageSize, status);
    return R.success(ordersPageInfo);
  }

//  小程序会员购买提交订单
  @PostMapping("/placeOrder")
  public R placeOrder(@RequestBody Orders orders){
    int shopCount = orders.getShopCount();
    Long shopId = orders.getShopId();
    Long userId = orders.getUserId();

    User user = userService.getById(userId);
    Shop shop = shopService.getById(shopId);
    if (shop.getStatus() == 1){
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setCreateTime(LocalDateTime.now());
      orderDetail.setEndTime(LocalDateTime.now());
      boolean b = orderDatailService.save(orderDetail);
      orders.setUserId(userId);
      orders.setShopId(shopId);
      orders.setShopCount(shopCount);
      orders.setAccount(shop.getNewPrice()*shopCount);
      orders.setStatus(1);
      orders.setShopName(shop.getShopName());
      orders.setPhone(user.getPhone());
      orders.setImage(user.getImage());
      orders.setPrice(shop.getNewPrice());
      orders.setOrderId(orderDetail.getId());
      if (b && ordersService.save(orders)){
        return R.success("下单成功");
      }else {
        return R.error("下单失败,请联系客服");
      }
    }else{
      return R.error("商品已停售");
    }
  }

//  小程序会员查询自己的订单
  @GetMapping
  public R findOrdersByUserId(@RequestParam Long userId){
    LambdaQueryWrapper<Orders> qw = new LambdaQueryWrapper<>();
    qw.eq(Orders::getUserId, userId);
    List<Orders> list = ordersService.list(qw);
    return R.success(list);
  }

//  小程序会员删除自己的订单
  @DeleteMapping
  public R deleteOrdersByUserId(@RequestParam Long id){
    LambdaQueryWrapper<Orders> qw = new LambdaQueryWrapper<>();
    qw.eq(Orders::getId, id);
    Orders orders = ordersService.getOne(qw);
    Long orderId = orders.getOrderId();
    LambdaQueryWrapper<OrderDetail> qw1 = new LambdaQueryWrapper<>();
    qw1.eq(OrderDetail::getId,orderId);
    boolean b1 = orderDatailService.remove(qw1);
    boolean b = ordersService.remove(qw);
    return (b==b1) ? R.success("删除成功"): R.error("删除失败");
  }
}
