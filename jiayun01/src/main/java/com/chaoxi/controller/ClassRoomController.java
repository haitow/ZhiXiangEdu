package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.ClassRoom;
import com.chaoxi.pojo.Notice;
import com.chaoxi.service.ClassRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("room")
public class ClassRoomController {
  @Autowired
  private ClassRoomService classRoomService;

//  分页查询
  @GetMapping("/page")
  public R page(Integer page,Integer pageSize,String name){
    Page<ClassRoom> roompage = new Page<>(page,pageSize);
    LambdaQueryWrapper<ClassRoom> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),ClassRoom::getName,name);
    qw.orderByDesc(ClassRoom::getUpdateTime);
    qw.orderByAsc(ClassRoom::getName);
    classRoomService.page(roompage,qw);
    return R.success(roompage);
  }

//  新增教室
  @PostMapping
  public R save(@RequestBody ClassRoom classRoom){
    boolean b = classRoomService.save(classRoom);
    return b? R.success("新增成功"): R.error("新增失败");
  }

//  根据id修改教室内容
  @GetMapping ("{id}")
  public R findById(@PathVariable Long id){
    return R.success(classRoomService.getById(id));
  }
  @PutMapping
  public R updateById(@RequestBody ClassRoom classRoom){
    boolean b = classRoomService.updateById(classRoom);
    return  b?R.success("修改成功"):R.error("修改失败");
  }

//  修改禁用信息
  @PutMapping("/change")
public R changes(@RequestBody ClassRoom classRoom){
  boolean b = classRoomService.updateById(classRoom);
  return  b?R.success("修改成功"):R.error("修改失败");
}

//  根据id删除
  @DeleteMapping
  public R deleteById(@RequestParam Long id){
    return R.success(classRoomService.removeById(id));
  }

  //课程新增
  @GetMapping
  public R list(){
    LambdaQueryWrapper<ClassRoom> qw = new LambdaQueryWrapper<>();
    qw.orderByDesc(ClassRoom::getName);
    List<ClassRoom> list = classRoomService.list();
    ArrayList<String> names = new ArrayList<>();
    for (ClassRoom room : list) {
      String name = room.getName();
      names.add(name);
    }
    return R.success(names);
  }
}
