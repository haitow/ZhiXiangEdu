package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.Classes;
import com.chaoxi.pojo.User;
import com.chaoxi.service.ClassesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/classes")
public class ClassesController {
  @Autowired
  private ClassesService classesService;

  //  分页查询
  @GetMapping("/page")
  public R page(Integer page, Integer pageSize, String name){
    Page<Classes> classespage = new Page<>(page, pageSize);
    LambdaQueryWrapper<Classes> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),Classes::getName,name);
    qw.orderByDesc(Classes::getUpdateTime);
    qw.orderByAsc(Classes::getName);
    classesService.page(classespage,qw);
    return R.success(classespage);
  }
  @GetMapping("/page1")
  public R<Page> page1(Integer page,Integer pageSize,String name){
    Page<Classes> classesPage = new Page<>(page,pageSize);
    LambdaQueryWrapper<Classes> lqw = new LambdaQueryWrapper<>();
    lqw.like(!StringUtils.isEmpty(name),Classes::getName,name);
    lqw.eq(Classes::getIsDeleted,1);
    lqw.orderByDesc(Classes::getUpdateTime);
    lqw.orderByAsc(Classes::getName);
    return R.success(classesService.page(classesPage,lqw));
  }
//新增课程
  @PostMapping
  public R add(@RequestBody Classes classes){
    boolean b = classesService.save(classes);
    return b? R.success("新增成功"): R.error("新增失败");
  }

//  修改课程
  @GetMapping("/{id}")
  public R page1(@PathVariable Long id){
    return R.success(classesService.getById(id));
  }
  @PutMapping
  public R update(@RequestBody Classes classes){
    boolean b = classesService.updateById(classes);
    return b?R.success("修改成功"):R.error("修改失败");
  }

//  删除移入回收站功能
  @DeleteMapping("/delete")
  public R changeStatusTo1(Long id){
    Classes classes = classesService.getById(id);
    classes.setIsDeleted(1);
    return classesService.updateById(classes)? R.success("删除成功"):R.error("删除失败");
  }
//  移出回收站
  @DeleteMapping("/restore")
  public R changeStatusTo0(Long id){
    Classes classes = classesService.getById(id);
    classes.setIsDeleted(0);
    return classesService.updateById(classes)? R.success("恢复成功"):R.error("恢复失败");
  }

  //  停用功能
  @PutMapping("/change")
  public R change(@RequestBody Classes classes){
    boolean b = classesService.updateById(classes);
    return  b?R.success("修改成功"):R.error("修改失败");
  }

//  小程序根据标签查询
  @GetMapping("/getClassByCardTags")
  public R getClassByCardTags(@RequestParam String tags){
    List<String> list = classesService.getClassByCardTags(tags);
    return R.success(list);
  }

//   小程序约课功能
  @GetMapping("/getClassList/{tags}")
  public R getClassListByTags(@PathVariable String tags){
    List<Classes> list = classesService.getClassListByTags(tags);
    return R.success(list);
  }

//   老师根据课程Id查询选课会员信息
  @GetMapping("/getUserByClassesId/{classId}")
  public R getUserByClassesId(@PathVariable Long classId){
    List<User> list = classesService.getUserByClassesId(classId);
    return R.success(list);
  }
}
