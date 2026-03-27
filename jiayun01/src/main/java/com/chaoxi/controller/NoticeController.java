package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.Notice;
import com.chaoxi.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeController {
  @Autowired
  private NoticeService noticeService;

//  通知分页查询
  @GetMapping("/page")
  public R pages(Integer page,Integer pageSize,String name){
    Page<Notice> NoticePage = new Page<>(page, pageSize);
    LambdaQueryWrapper<Notice> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),Notice::getTitle,name);
    qw.orderByDesc(Notice::getUpdateTime);
    qw.orderByAsc(Notice::getTitle);
    noticeService.page(NoticePage,qw);
    return R.success(NoticePage);
  }

//  新增通知
  @PostMapping
  public R InsertNotice(@RequestBody Notice notice){
    boolean b = noticeService.save(notice);
    return b?R.success("新增成功"):R.error("新增失败");
  }


//  根据id修改通知
  @GetMapping("{id}")
  public R findById(@PathVariable Long id){
    return R.success(noticeService.getById(id));
  }
  @PutMapping
  public R updateById(@RequestBody Notice notice){
    boolean b = noticeService.updateById(notice);
    return  b?R.success("修改成功"): R.error("修改失败");
  }

//  根据id删除
  @DeleteMapping
  public R deleteById(@RequestParam Long id){
    return R.success(noticeService.removeById(id));
  }

//  小程序查询通知
  @GetMapping
  public R findNotice(){
    List<Notice> list = noticeService.list();
    return R.success(list);
  }
}
