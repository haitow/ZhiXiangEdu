package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.CodeUtils;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

//  分页查询
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

//  新增老师 管理端
  @PostMapping("/teacher")
  public R saveTeacher(@RequestBody User user){
    user.setType("老师");
    user.setUpdateTime(LocalDateTime.now());
    return userService.save(user)?R.success("新增成功"):R.error("新增失败");
  }

// 根据id获取用户信息 管理端 用户端
  @GetMapping("/teacher/{id}")
  public R getTeacherById(@PathVariable Long id){
    return R.success(userService.getById(id));
  }
//修改老师 管理端 用户端
  @PutMapping("/teacher")
  public R updateTeacher(@RequestBody User user){
    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
    lqw.eq(User::getId,user.getId());
    return userService.update(user,lqw)?R.success("修改成功"):R.error("修改失败");
  }


//  新增会员 管理端
  @PostMapping("/user")
  public R saveUser(@RequestBody User user){
    user.setType("会员");
    user.setUpdateTime(LocalDateTime.now());
    return userService.save(user)?R.success("新增成功"):R.error("新增失败");
  }

//  根据id获取会员信息 管理端 用户端
  @GetMapping("/user/{id}")
  public R getUserById(@PathVariable Long id){
    return R.success(userService.getById(id));
  }
// 修改会员 管理端 用户端
  @PutMapping("/student")
  public R updateStudent(@RequestBody User user){
    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
    lqw.eq(User::getId,user.getId());
    return userService.update(user,lqw)?R.success("修改成功"):R.error("修改失败");
  }

// 修改老师/会员状态
  @PutMapping("/status")
  public R updateStatus(@RequestBody User user){
    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
    lqw.eq(User::getId,user.getId());
    user.setUpdateTime(LocalDateTime.now());
    return userService.update(user,lqw)?R.success("修改成功"):R.error("修改失败");
  }

  // 查询老师
  @GetMapping("/teacher")
  public R getTeacherList(){
    LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
    qw.eq(User::getType,"老师");
    qw.orderByDesc(User::getName);
    List<User> list = userService.list(qw);
    //List<String>
    List<String> names = new ArrayList<>();
    for (User user : list) {
      String name = user.getName();
      names.add(name);
    }
    return R.success(names);
  }

//  根据会员id查询会员已办卡
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

//  根据会员id为用户选择卡
  @PostMapping("/user/saveByCardId/{cardid}")
  public R saveUserCardByCardId(@PathVariable Long cardid,@RequestBody User user){
    boolean b = userService.saveUserCardByCardId(cardid,user);
    return b? R.success("添加成功"): R.error("添加失败");
  }

//  根据会员id查询会员所选课程
  @GetMapping("/userClasses/{userId}")
  public R getUserClassByUserId(@PathVariable Long userId){
    List<Classes> list = userService.getUserClassByUserId(userId);
    return R.success(list);
  }

//  根据老师id查询该老师的课程
  @GetMapping("/teacher/classes/{tId}")
  public R getTeacherClassByTeacherId(@PathVariable Long tId){
    List<Classes> list = userService.getTeacherClassByTeacherId(tId);
    return R.success(list);
  }

  @Autowired
  private RedisTemplate redisTemplate;
//  小程序短信验证码
  @PostMapping("/sendMsg")
  public R sendMsg(@RequestBody Map<String,String> map){
//    获取手机号
    String phone = map.get("phone");
//    判断手机号格式
    if (!RegexUtils.isPhoneNumber(phone)){
      return R.error("手机号格式有误");
    }
//    生成验证码
    Integer code = CodeUtils.generateValidateCode(4);
    log.info("code:{}",code);
//    将验证码存入redis
    redisTemplate.opsForValue().set("code"+phone,
        code,
        1,
        TimeUnit.MINUTES
    );
//    发送短信
//    try {
//      SMSSendCode.sendCode(phone,String.valueOf(code));
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }

//  短信发送成功
    return R.success("短信发送成功");
  }

//  小程序登录
  @PostMapping("/login")
  public R login(@RequestBody Map<String,String> map){
//    获取手机号
    String phone = map.get("phone");
//    获取验证码
    String code = map.get("code");
//    校验手机号
    if (!RegexUtils.isPhoneNumber(phone)){
      return R.error("手机格式有误");
    }
//    校验验证码
    Object Rcode = redisTemplate.opsForValue().get("code"+phone);
    if (StringUtils.isEmpty(Rcode)){
      return R.error("验证码已过期");
    }
    if (StringUtils.isEmpty(code)){
      return R.error("验证码不能为空");
    }
//    判断类型(会员or老师)
    String type = map.get("type");
    if (StringUtils.isEmpty(type)){
      return R.error("请勾选类型");
    }
//    从数据库中根据手机号查询用户数据
    LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
    qw.eq(User::getPhone,phone);
    qw.eq(User::getType,type);
    User user = userService.getOne(qw);
//    如果是会员,则进行注册加登录功能
    if (user == null && type.equals("会员")){
      user = new User();
//      注册
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
//      联系管理员
      R.error("请老师联系管理员");
    }
//    判断是否禁用
    if (user.getStatus() == 0){
      return R.error("该用户已被禁用");
    }
//    将登录数据存入redis
    redisTemplate.opsForValue().set(
        "auth"+user.getId(),
        user.getId(),
        7,
        TimeUnit.DAYS
    );
    return R.success(user);
  }

//  根据用户Id和卡的Id查询此人是否拥有此卡
  @GetMapping("/getSelectedCard")
  public R getSelectedCard(@RequestParam Long userId,
                           @RequestParam Long cardId)
  {
    boolean b = userService.getSelectedCard(userId,cardId);
    return R.success(b);
  }

//  小程序分页查询老师信息
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

//  //  根据课程Id查询用户信息
//  @GetMapping("/getUserByClassesId/{classId}")
//  public R getUserByClassesId(@PathVariable Long classId){
//    List<UserClass> list = userService.getUserByClassesId(classId);
//    return R.success(list);
//  }
}
