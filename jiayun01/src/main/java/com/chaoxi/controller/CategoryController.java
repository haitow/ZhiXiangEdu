package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.Category;
import com.chaoxi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

//  分页查询
  @GetMapping("/page")
  public R page(Integer page,Integer pageSize,String name){
    Page<Category> fenyepage = new Page<>(page, pageSize);
    LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),Category::getName,name);
    qw.orderByDesc(Category::getUpdateTime);
    qw.orderByAsc(Category::getName);
    categoryService.page(fenyepage,qw);
    return R.success(fenyepage);
  }

//  新增功能
  @PostMapping
  public R save(@RequestBody Category category){
    boolean b = categoryService.save(category);
//    LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
//    lqw.eq(Category::getName,category.getName());
//    Category one = categoryService.getOne(lqw);
//    one.getId()
    return b? R.success("新增成功"): R.error("新增失败");
  }

//  根据id进行修改
  @GetMapping("{id}")
  public R findById(@PathVariable Long id){
    return  R.success(categoryService.getById(id));
  }
  @PutMapping
  public R updateById(@RequestBody Category category){
    boolean b = categoryService.updateById(category);
    return b? R.success("修改成功"): R.error("修改失败");
  }

//  根据id删除
  @DeleteMapping
  public R deleteById(@RequestParam Long id){
    boolean b = categoryService.removeById(id);
    return b? R.success("删除成功"): R.error("删除失败");
  }

//全部分类查询
  @GetMapping
  public R findAll(){
//    LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
//    qw.orderByDesc(Category::getName);
//    List<Category> list = categoryService.list(qw);
//    //List<String>
//    ArrayList<Long> ids = new ArrayList<>();
//    for (Category category : list) {
//      Long idss = category.getId();
//      ids.add(idss);
//    }
//    return R.success(ids);
    List<Category> list = categoryService.list();
    return R.success(list);
  }
}
