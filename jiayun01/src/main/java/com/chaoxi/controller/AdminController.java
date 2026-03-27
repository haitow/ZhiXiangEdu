package com.chaoxi.controller;


import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.Admin;
import com.chaoxi.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
  @Autowired
  private AdminService adminService;
  @Autowired
  private RedisTemplate redisTemplate;
//   登录功能
  @PostMapping("/login")
  public R login(@RequestBody Admin admin){
//    1.接收参数
    String username = admin.getUsername();
    String password = admin.getPassword();
//    加密比较
    password = MD5.create().digestHex(password);
//    2.根据用户名查询用户
    LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
    qw.eq(Admin::getUsername,username);
    Admin admin1 = adminService.getOne(qw);
//    判断
    if (StringUtils.isEmpty(admin1)){
      return R.error("用户不存在");
    }
    if (StringUtils.isEmpty(password) || !admin1.getPassword().equals(password)){
      return R.error("密码错误");
    }
    if (admin1.getStatus() == 0){
      return R.error("该用户被禁用");
    }
//    把用户名称储存在数据库
//    session.setAttribute("user",admin1.getId());
    redisTemplate.opsForValue().set(
        "auth"+admin1.getId(),
        admin1.getId(),
        30,
        TimeUnit.MINUTES
    );
//    把用户返回前台储存回显
    return R.success(admin1.getId());
  }

//  退出登录功能
  @GetMapping("/logout")
  public R logut(HttpServletRequest request){
//    session.removeAttribute("user");
    String auth = request.getHeader("auth");
    if (!StringUtils.isEmpty(auth)){
      redisTemplate.delete("auth"+auth);
    }
    return R.success("退出成功");
  }

//  管理员分页查询
  @GetMapping("/page")
  public R page(Integer page,Integer pageSize,String name){
    Page<Admin> adminPage = new Page<>(page, pageSize);
    LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),Admin::getName,name);
    qw.orderByDesc(Admin::getUpdateTime);
    qw.orderByAsc(Admin::getName);
    adminService.page(adminPage,qw);
    return R.success(adminPage);
  }

  // 新增管理员
  @PostMapping
  public R save(@RequestBody Admin admin){
    // 1. 接受数据
    log.info("新增数据：{}",admin);

    // 2. 维护字段
    admin.setStatus(1);

    //密码加密
    admin.setPassword(MD5.create().digestHex(admin.getPassword()));

//    admin.setCreateTime(LocalDateTime.now());
//    admin.setUpdateTime(LocalDateTime.now());
//
//    admin.setCreateUser((Long) session.getAttribute("user"));
//    admin.setUpdateUser((Long) session.getAttribute("user"));
    // 3. 新增
    boolean b = adminService.save(admin);

    // 返回
    return b?R.success("新增成功"):R.error("新增失败");
  }

//  启用/禁用管理员权限
@PutMapping("/changeStatus")
public R changeStatus(@RequestBody Admin admin){
  log.info("修改数据：{}",admin);
  // 修改 - 字段维护
//    admin.setUpdateTime(LocalDateTime.now());
//    admin.setUpdateUser((Long) session.getAttribute("user"));
  boolean b = adminService.updateById(admin);
  return b?R.success("修改成功"):R.error("修改失败");
}

//根据id查询管理员信息
@GetMapping("/{id}")
public R getById(@PathVariable Long id){
  log.info("查询数据：{}",id);
  return R.success(adminService.getById(id));
}

//修改管理员信息
  @PutMapping
  public R update(@RequestBody Admin admin) throws NoSuchAlgorithmException {
    if(admin.getPassword().length() <= 30){
      admin.setPassword(MD5.create().digestHex(admin.getPassword()));
    }
    boolean b = adminService.updateById(admin);
    return b? R.success("修改成功"): R.error("修改失败");
  }

//  小程序联系管理员
  @GetMapping
  public R getAll(){
    LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
    qw.select(Admin::getName,Admin::getPhone);
    List<Admin> list = adminService.list(qw);
    return R.success(list);
  }

}
