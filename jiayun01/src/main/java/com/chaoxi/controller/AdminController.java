package com.chaoxi.controller;


import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.JwtUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
  @Autowired
  private AdminService adminService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private JwtUtils jwtUtils;

  @PostMapping("/login")
  public R login(@RequestBody Admin admin){
    String username = admin.getUsername();
    String password = admin.getPassword();
    password = MD5.create().digestHex(password);
    LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
    qw.eq(Admin::getUsername,username);
    Admin admin1 = adminService.getOne(qw);
    if (StringUtils.isEmpty(admin1)){
      return R.error("用户不存在");
    }
    if (StringUtils.isEmpty(password) || !admin1.getPassword().equals(password)){
      return R.error("密码错误");
    }
    if (admin1.getStatus() == 0){
      return R.error("该用户被禁用");
    }
    String accessToken = jwtUtils.generateAccessToken(admin1.getId(), "admin");
    String refreshToken = jwtUtils.generateRefreshToken(admin1.getId(), "admin");
    redisTemplate.opsForValue().set(
        "refresh:admin:"+admin1.getId(),
        refreshToken,
        7,
        TimeUnit.DAYS
    );
    Map<String, Object> result = new HashMap<>();
    result.put("accessToken", accessToken);
    result.put("refreshToken", refreshToken);
    result.put("id", admin1.getId());
    result.put("name", admin1.getName());
    result.put("userType", "admin");
    return R.success(result);
  }

  @GetMapping("/logout")
  public R logut(HttpServletRequest request){
    String token = request.getHeader("Authorization");
    if (!StringUtils.isEmpty(token) && token.startsWith("Bearer ")){
      token = token.substring(7);
      try{
        Long userId = jwtUtils.getUserIdFromToken(token);
        redisTemplate.delete("refresh:admin:"+userId);
      }catch (Exception e){
        log.error("退出登录解析token异常",e);
      }
    }
    return R.success("退出成功");
  }

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

  @PostMapping
  public R save(@RequestBody Admin admin){
    log.info("新增数据：{}",admin);
    admin.setStatus(1);
    admin.setPassword(MD5.create().digestHex(admin.getPassword()));
    boolean b = adminService.save(admin);
    return b?R.success("新增成功"):R.error("新增失败");
  }

  @PutMapping("/changeStatus")
  public R changeStatus(@RequestBody Admin admin){
    log.info("修改数据：{}",admin);
    boolean b = adminService.updateById(admin);
    return b?R.success("修改成功"):R.error("修改失败");
  }

  @GetMapping("/{id}")
  public R getById(@PathVariable Long id){
    log.info("查询数据：{}",id);
    return R.success(adminService.getById(id));
  }

  @PutMapping
  public R update(@RequestBody Admin admin) throws NoSuchAlgorithmException {
    if(admin.getPassword().length() <= 30){
      admin.setPassword(MD5.create().digestHex(admin.getPassword()));
    }
    boolean b = adminService.updateById(admin);
    return b? R.success("修改成功"): R.error("修改失败");
  }

  @GetMapping
  public R getAll(){
    LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
    qw.select(Admin::getName,Admin::getPhone);
    List<Admin> list = adminService.list(qw);
    return R.success(list);
  }

  @PostMapping("/register")
  public R register(@RequestBody Admin admin){
    log.info("注册数据：{}",admin);
    LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
    qw.eq(Admin::getUsername, admin.getUsername());
    Admin existAdmin = adminService.getOne(qw);
    if (existAdmin != null){
      return R.error("用户名已存在");
    }
    admin.setStatus(1);
    admin.setPassword(MD5.create().digestHex(admin.getPassword()));
    boolean b = adminService.save(admin);
    return b?R.success("注册成功"):R.error("注册失败");
  }

  @GetMapping("/security/question")
  public R getSecurityQuestion(@RequestParam String username){
    LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
    qw.eq(Admin::getUsername, username);
    Admin admin = adminService.getOne(qw);
    if (admin == null){
      return R.error("用户不存在");
    }
    if (StringUtils.isEmpty(admin.getSecurityQuestion())){
      return R.error("该用户未设置密保问题");
    }
    return R.success(admin.getSecurityQuestion());
  }

  @PostMapping("/security/verify")
  public R verifySecurityAnswer(@RequestBody Map<String, String> body){
    String username = body.get("username");
    String securityAnswer = body.get("securityAnswer");
    LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
    qw.eq(Admin::getUsername, username);
    Admin admin = adminService.getOne(qw);
    if (admin == null){
      return R.error("用户不存在");
    }
    if (StringUtils.isEmpty(admin.getSecurityAnswer()) || !admin.getSecurityAnswer().equals(securityAnswer)){
      return R.error("密保答案错误");
    }
    return R.success(admin.getId());
  }

  @PostMapping("/password/reset")
  public R resetPassword(@RequestBody Map<String, Object> body){
    Long userId = Long.valueOf(body.get("userId").toString());
    String newPassword = body.get("newPassword").toString();
    Admin admin = adminService.getById(userId);
    if (admin == null){
      return R.error("用户不存在");
    }
    admin.setPassword(MD5.create().digestHex(newPassword));
    boolean b = adminService.updateById(admin);
    return b?R.success("密码重置成功"):R.error("密码重置失败");
  }

}
