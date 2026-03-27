package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.Activity;
import com.chaoxi.pojo.Card;
import com.chaoxi.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/activity")
public class ActivityController {
  @Autowired
  private ActivityService activityService;

//  分页查询
  @GetMapping("/page")
  public R page(Integer page,Integer pageSize,String name){
    Page<Activity> activityPage = new Page<>(page, pageSize);
    LambdaQueryWrapper<Activity> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),Activity::getActivityName,name);
    qw.orderByDesc(Activity::getEndTime);
    qw.orderByAsc(Activity::getActivityName);
    activityService.page(activityPage,qw);
    return R.success(activityPage);
  }

//  新增活动
  @PostMapping
  public R save(@RequestBody Activity activity){
    boolean b = activityService.save(activity);
    return b? R.success("新增成功"): R.error("新增失败");
  }


  //修改活动
  @GetMapping("/{id}")
  public R getCardById(@PathVariable Long id){
    Activity activity = activityService.getById(id);
    return R.success(activity);
  }
  @PutMapping
  public R update(@RequestBody Activity activity){
    boolean b = activityService.updateById(activity);
    return b?R.success("修改成功"): R.error("修改失败");
  }

//  停用功能
  @PutMapping("/change")
  public R change(@RequestBody Activity activity){
    boolean b = activityService.updateById(activity);
    return b? R.success("停用成功"): R.error("停用失败");
  }

//  删除功能
  @DeleteMapping
  public R deleteById(@RequestParam Long id){
    boolean b = activityService.removeById(id);
    return b? R.success("删除成功"): R.error("删除失败");
  }

//  小程序查询所有活动
  @GetMapping("/getActivityList")
  public R getActivityList(){
    List<Activity> list = activityService.list();
    return R.success(list);
  }
}
