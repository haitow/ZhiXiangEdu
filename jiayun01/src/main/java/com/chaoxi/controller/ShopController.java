package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.Shop;
import com.chaoxi.pojo.Tags;
import com.chaoxi.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shop")
public class ShopController {
  @Autowired
  private ShopService shopService;

//  分页查询
  @GetMapping("/page")
  public R page(Integer page,Integer pageSize,String name){
    Page<Shop> shopPage = new Page<>(page, pageSize);
    LambdaQueryWrapper<Shop> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),Shop::getShopName,name);
    qw.orderByDesc(Shop::getCategoryId);
    qw.orderByAsc(Shop::getShopName);
    shopService.page(shopPage,qw);
    return R.success(shopPage);
  }

//  新增功能
  @PostMapping
  public R save(@RequestBody Shop shop){
    boolean b = shopService.save(shop);
    return b? R.success("新增成功"): R.error("新增失败");
  }

  //  根据id进行修改
  @GetMapping("{id}")
  public R findById(@PathVariable Long id){
    return R.success(shopService.getById(id));
  }
//  @PutMapping
//  public R updateById(@RequestBody Shop shop){
//    boolean b = shopService.updateById(shop);
//    return b? R.success("修改成功"): R.error("修改失败");
//  }

//  停用功能
  @PutMapping("/change")
  public R change(@RequestBody Shop shop){
    boolean b = shopService.updateById(shop);
    return b? R.success("停用成功"): R.error("停用失败");
  }

//  删除功能
  @DeleteMapping
  public R deleteById(@RequestParam Long id){
    boolean b = shopService.removeById(id);
    return b? R.success("删除成功"): R.error("删除失败");
  }

//  小程序商品查询功能
  @GetMapping("/getShopByCategoryId/{categoryId}")
  public R getShopByCategoryId(@PathVariable Long categoryId){
    LambdaQueryWrapper<Shop> qw = new LambdaQueryWrapper<>();
    qw.eq(Shop::getCategoryId,categoryId);
    List<Shop> shops = shopService.list(qw);
    return R.success(shops);
  }
}
