package com.chaoxi.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chaoxi.common.R;
import com.chaoxi.pojo.Classes;
import com.chaoxi.pojo.User;
import com.chaoxi.pojo.UserCard;
import com.chaoxi.pojo.UserClass;
import com.chaoxi.service.ClassesService;
import com.chaoxi.service.UserCardService;
import com.chaoxi.service.UserClassService;
import com.chaoxi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/userClasses")
public class UserClassController {
  @Autowired
  private UserClassService userClassService;
  @Autowired
  private ClassesService classesService;
  @Autowired
  private UserCardService userCardService;
  @Autowired
  private UserService userService;

  //  根据用户Id查询用户选课信息
  @GetMapping("/{userId}")
  public R<List<UserClass>> getSelectedClassesByUserId(@PathVariable Long userId){
    LambdaQueryWrapper<UserClass> lqw = new LambdaQueryWrapper<>();
    lqw.eq(UserClass::getUserId,userId);
    return R.success(userClassService.list(lqw));
  }

//  会员选课功能
  @PostMapping("/chooseClasses")
  public R chooseClasses(@RequestBody Map<String,Long> map){
//    1.用户信息: 获得用户Id,课程Id
    Long userId = map.get("userId");
    Long classId = map.get("classId");
//    2.查询课程信息
    Classes classes = classesService.getById(classId);
//    3.判断课程是否过期
    if (!classes.getEndTime().isAfter(LocalDateTime.now())){
      return R.error("课程已过期或已结束");
    }
//    4.判断课程是否满员
    if (classes.getPnum()>=classes.getPmax()){
      return R.error("选课人数已达上限");
    }
//    5.根据用户Id获取用户卡片信息
    LambdaQueryWrapper<UserCard> qwUserCard = new LambdaQueryWrapper<>();
    qwUserCard.eq(UserCard::getUserId,userId);
    List<UserCard> userCards = userCardService.list(qwUserCard);
//    6.遍历卡片信息,把所有不可用条件过滤掉,最后获取可用的卡片集合
//    在userCard 字段添加一个disabled字段但是数据库不可见加入mp注解标识@TableField(exist = false)
    List<UserCard> userCardOk = userCards.stream()
        .filter(item->{//过滤过期卡
          return item.getEndDate().isAfter(LocalDateTime.now());
        }).filter(item->{//卡片标签比对
          String[] tags_card = item.getTags().split(",");
          String[] tags_classes= classes.getTags().split(",");
//        连个集合取交集 卡片可用的筛选出来
          Collection<String> intersection = CollectionUtil
              .intersection(Arrays.asList(tags_card), Arrays.asList(tags_classes));
          return intersection.size()>0;
        }).filter(item->{
          boolean flag = false;
          if (item.getStatus() == 1){
            R.error("此课程已停课");
            flag = true;
          }
          return flag;
        }).filter(item->{//把没有次数的卡过滤掉
          boolean flag = false;
          if (item.getType().equals("期限卡")) {
            item.setDisabled(true);
            flag = true;
          }else if (item.getType().equals("次卡") && item.getCount() >= 1){
            item.setDisabled(true);
            flag = true;
          }
          return flag;
        }).map(item->{
          // 最后把所有可用的卡片收集出来
          item.setDisabled(true);
          return item;
        }).toList();
    //7.如果集合为null 或者集合为空 则无权限卡
    if(userCardOk == null || CollectionUtil.isEmpty(userCardOk)){
      return R.error("无权限卡");
    }
    //8.遍历所有可用卡片
    UserCard thisUserCard = null;
    //8.1若果这个卡为期限卡，立即使用
    for (UserCard userCard : userCardOk) {
      if (userCard.getType().equals("期限卡")){
        thisUserCard = userCard;
        break;
      }
    }
    //8.2如果没有期限卡 使用次卡
    if (thisUserCard == null){
      for (UserCard userCard : userCardOk) {
        if (userCard.getType().equals("次卡")){
          thisUserCard = userCard;
          thisUserCard.setCount(userCard.getCount()-1);
          userCardService.updateById(thisUserCard);
          break;
        }
      }
    }
    //9.把会员添加到课程中
    User user = userService.getById(userId);//获取用户信息
    UserClass userClass1 = new UserClass();
    userClass1.setUserId(userId);
    userClass1.setClassId(classId);
    userClass1.setClassName(classes.getName());
    userClass1.setUserName(user.getName());
    userClass1.setCreateTime(LocalDateTime.now());
    userClass1.setStatus(1);
    userClass1.setIsDeleted(0);
    userClass1.setTags(classes.getTags());
    boolean save = userClassService.save(userClass1);
    return R.success("选课成功");
    }

//    根据老师Id查询总课时
  @GetMapping("/getClassesTotalCount/{userId}")
  public R getClassesTotalCount(@PathVariable Long userId){
    return R.success(userClassService.getClassesTotalCount(userId));
  }
//  根据老师Id查询本月课时
  @GetMapping("/getMouthTotal/{userId}")
  public R getMouthTotal(@PathVariable Long userId){
    return R.success(userClassService.getMouthTotal(userId));
  }
  // 获取老师的课时统计信息
  @GetMapping("/getTeacherCount/{userId}")
  public R getTeacherCount(@PathVariable Long userId){
    HashMap<String,Long> count = userClassService.getTeacherCount(userId);
    return R.success(count);
  }
//  老师修改学生缺勤出勤
  @PutMapping("/attendance")
  public R attendance(UserClass userClass){
    LambdaQueryWrapper<UserClass> qw = new LambdaQueryWrapper<>();
    qw.eq(UserClass::getUserId,userClass.getUserId());
    qw.eq(UserClass::getClassId,userClass.getClassId());
    boolean b = userClassService.update(userClass, qw);
    return b? R.success("修改成功"): R.error("修改失败");
  }
}
