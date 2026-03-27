package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.Tags;
import com.chaoxi.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/tags")
public class TagsController {
  @Autowired
  private TagsService tagsService;

//  分页查询
  @GetMapping("/page")
  public R page(Integer page,Integer pageSize,String name){
    Page<Tags> tagspage = new Page<>(page,pageSize);
    LambdaQueryWrapper<Tags> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),Tags::getName,name);
    qw.orderByDesc(Tags::getUpdateTime);
    qw.orderByAsc(Tags::getName);
    tagsService.page(tagspage,qw);
    return R.success(tagspage);
  }

//  新增标签
  @PostMapping
  public R save(@RequestBody Tags tags){
    boolean b = tagsService.save(tags);
    return b? R.success("新增成功"): R.error("新增失败");
  }

//  根据id进行修改
  @GetMapping("{id}")
  public R findById(@PathVariable Long id){
    return R.success(tagsService.getById(id));
  }
  @PutMapping
  public R updateById(@RequestBody Tags tags){
    boolean b = tagsService.updateById(tags);
    return b? R.success("修改成功"): R.error("修改失败");
  }

//  根据id删除
  @DeleteMapping
  public R deleteById(@RequestParam Long id){
    return R.success(tagsService.removeById(id));
  }
//会员新增
  @GetMapping
  public R list(){
    return R.success(tagsService.list());
  }
}
