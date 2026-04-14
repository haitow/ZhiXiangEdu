package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.CodeUtils;
import com.chaoxi.common.JwtUtils;
import com.chaoxi.common.R;
import com.chaoxi.common.RegexUtils;
import com.chaoxi.pojo.Classes;
import com.chaoxi.pojo.User;
import com.chaoxi.pojo.UserCard;
import com.chaoxi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private JwtUtils jwtUtils;

  @GetMapping("/page")
  public R page(
      @RequestParam Integer page,
      @RequestParam Integer pageSize,
      @RequestParam(required = false) String name,
      @RequestParam String type){
    Page<User> userpage = new Page<>(page, pageSize);
    LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),User::getName,name);
    qw.eq(User::getType,type);
    qw.orderByDesc(User::getUpdateTime);
    qw.orderByAsc(User::getName);
    userService.page(userpage,qw);
    return R.success(userpage);
  }

  @PostMapping("/teacher")
  public R saveTeacher(@RequestBody User user){
    user.setType("老师");
    user.setUpdateTime(LocalDateTime.now());
    return userService.save(user)?R.success("新增成功"):R.error("新增失败");
  }

  @GetMapping("/teacher/{id}")
  public R getTeacherById(@PathVariable Long id){
    return R.success(userService.getById(id));
  }

  @PutMapping("/teacher")
  public R updateTeacher(@RequestBody User user){
    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
    lqw.eq(User::getId,user.getId());
    return userService.update(user,lqw)?R.success("修改成功"):R.error("修改失败");
  }


  @PostMapping("/user")
  public R saveUser(@RequestBody User user){
    user.setType("会员");
    user.setUpdateTime(LocalDateTime.now());
    return userService.save(user)?R.success("新增成功"):R.error("新增失败");
  }

  @GetMapping("/user/{id}")
  public R getUserById(@PathVariable Long id){
    return R.success(userService.getById(id));
  }

  @PutMapping("/student")
  public R updateStudent(@RequestBody User user){
    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
    lqw.eq(User::getId,user.getId());
    return userService.update(user,lqw)?R.success("修改成功"):R.error("修改失败");
  }

  @PutMapping("/status")
  public R updateStatus(@RequestBody User user){
    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
    lqw.eq(User::getId,user.getId());
    user.setUpdateTime(LocalDateTime.now());
    return userService.update(user,lqw)?R.success("修改成功"):R.error("修改失败");
  }

  @GetMapping("/teacher")
  public R getTeacherList(){
    LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
    qw.eq(User::getType,"老师");
    qw.orderByDesc(User::getName);
    List<User> list = userService.list(qw);
    List<String> names = new ArrayList<>();
    for (User user : list) {
      String name = user.getName();
      names.add(name);
    }
    return R.success(names);
  }

  @GetMapping("/userCards/{userid}")
  public R getUserCardsByUserId(@PathVariable Long userid){
    List<UserCard> cardList = userService.getUserCardsByUserId(userid);
    for (UserCard list : cardList) {
      Long userId = list.getUserId();
      Long cardId = list.getCardId();
      String cardName = list.getCardName();
      String tags = list.getTags();
      String type = list.getType();
      Integer count = list.getCount();
      Integer status = list.getStatus();
      BigDecimal price = list.getPrice();
      String image = list.getImage();
      Integer isDeleted = list.getIsDeleted();
      UserCard userCard = new UserCard();
      userCard.setUserId(userId);
      userCard.setCardId(cardId);
      userCard.setCardName(cardName);
      userCard.setTags(tags);
      userCard.setCount(count);
      userCard.setStatus(status);
      userCard.setPrice(price);
      userCard.setImage(image);
      userCard.setIsDeleted(isDeleted);
      userCard.setType(type);
    }
    return R.success(cardList);
  }

  @PostMapping("/user/saveByCardId/{cardid}")
  public R saveUserCardByCardId(@PathVariable Long cardid,@RequestBody User user){
    boolean b = userService.saveUserCardByCardId(cardid,user);
    return b? R.success("添加成功"): R.error("添加失败");
  }

  @GetMapping("/userClasses/{userId}")
  public R getUserClassByUserId(@PathVariable Long userId){
    List<Classes> list = userService.getUserClassByUserId(userId);
    return R.success(list);
  }

  @GetMapping("/teacher/classes/{tId}")
  public R getTeacherClassByTeacherId(@PathVariable Long tId){
    List<Classes> list = userService.getTeacherClassByTeacherId(tId);
    return R.success(list);
  }

  @PostMapping("/sendMsg")
  public R sendMsg(@RequestBody Map<String,String> map){
    String phone = map.get("phone");
    if (!RegexUtils.isPhoneNumber(phone)){
      return R.error("手机号格式有误");
    }
    Integer code = CodeUtils.generateValidateCode(4);
    log.info("code:{}",code);
    System.out.println(code);
    redisTemplate.opsForValue().set("code"+phone,
        code,
        1,
        TimeUnit.MINUTES
    );
    return R.success("短信发送成功");
  }

  @PostMapping("/login")
  public R login(@RequestBody Map<String,String> map){
    String phone = map.get("phone");
    String code = map.get("code");
    if (!RegexUtils.isPhoneNumber(phone)){
      return R.error("手机格式有误");
    }
    Object Rcode = redisTemplate.opsForValue().get("code"+phone);
    if (StringUtils.isEmpty(Rcode)){
      return R.error("验证码已过期");
    }
    if (StringUtils.isEmpty(code)){
      return R.error("验证码不能为空");
    }
    if (!Rcode.toString().equals(code)){
      return R.error("验证码错误");
    }
    redisTemplate.delete("code"+phone);
    String type = map.get("type");
    if (StringUtils.isEmpty(type)){
      return R.error("请勾选类型");
    }
    LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
    qw.eq(User::getPhone,phone);
    qw.eq(User::getType,type);
    User user = userService.getOne(qw);
    if (user == null && type.equals("会员")){
      user = new User();
      user.setImage("a.png");
      user.setType(type);
      user.setPhone(phone);
      user.setName("会员"+phone);
      user.setStatus(1);
      user.setBirthday(LocalDate.now());
      user.setCreateTime(LocalDateTime.now());
      user.setUpdateTime(LocalDateTime.now());
      user.setCreateUser(2L);
      user.setUpdateUser(2L);
      userService.save(user);
    }else if (user == null && type.equals("老师")){
      R.error("请老师联系管理员");
    }
    if (user.getStatus() == 0){
      return R.error("该用户已被禁用");
    }
    String accessToken = jwtUtils.generateAccessToken(user.getId(), "user");
    String refreshToken = jwtUtils.generateRefreshToken(user.getId(), "user");
    redisTemplate.opsForValue().set(
        "refresh:user:"+user.getId(),
        refreshToken,
        7,
        TimeUnit.DAYS
    );
    Map<String, Object> result = new HashMap<>();
    result.put("accessToken", accessToken);
    result.put("refreshToken", refreshToken);
    result.put("id", user.getId());
    result.put("name", user.getName());
    result.put("type", user.getType());
    result.put("phone", user.getPhone());
    result.put("image", user.getImage());
    result.put("userType", "user");
    return R.success(result);
  }

  @GetMapping("/getSelectedCard")
  public R getSelectedCard(@RequestParam Long userId,
                           @RequestParam Long cardId)
  {
    boolean b = userService.getSelectedCard(userId,cardId);
    return R.success(b);
  }

  @GetMapping("/teacher/page")
  public R pageTeacher(Integer page,Integer pageSize,String name){
    Page<User> teacherPage = new Page<>(page, pageSize);
    LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
    qw.eq(User::getType,"老师");
    qw.like(!StringUtils.isEmpty(name),User::getName,name);
    qw.orderByDesc(User::getUpdateTime);
    qw.orderByAsc(User::getName);
    userService.page(teacherPage,qw);
    return R.success(teacherPage);
  }

}
