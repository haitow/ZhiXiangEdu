package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.common.BaseContext;
import com.chaoxi.mapper.ClassesMapper;
import com.chaoxi.pojo.Classes;
import com.chaoxi.pojo.User;
import com.chaoxi.pojo.UserClass;
import com.chaoxi.service.ClassesService;
import com.chaoxi.service.UserClassService;
import com.chaoxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassesServiceimpl
    extends ServiceImpl<ClassesMapper, Classes>
    implements ClassesService {
  @Override
  public List<String> getClassByCardTags(String tags) {
//    将标签tags 撕裂
    String[] tagNames = tags.split(",");
//    根据标签,找到相关课程
    LambdaQueryWrapper<Classes> qw = new LambdaQueryWrapper<>();
//    投影查询,提高查询效率
    qw.select(Classes::getName);
    for (String tagName : tagNames) {
      qw.like(Classes::getTags,tagName).or();
    }
    List<String> list = this.listObjs(qw);
    return list;
  }

  @Autowired
  private UserClassService userClassService;
  @Override
  public List<Classes> getClassListByTags(String tags) {
//    获取用户Id
    Long userId = BaseContext.getCurrentId();
//    根据用户Id查询已选课程Id
    LambdaQueryWrapper<UserClass> qw1 = new LambdaQueryWrapper<>();
    qw1.select(UserClass::getClassId);
    qw1.eq(UserClass::getUserId,userId);
    List<Long> classIds = userClassService.listObjs(qw1);
//    根据标签查询课程
    LambdaQueryWrapper<Classes> qw = new LambdaQueryWrapper<>();
    qw.like(Classes::getTags,tags);
//    排除用户已选课程
    qw.notIn(!StringUtils.isEmpty(classIds) && classIds.size() != 0,
        Classes::getId,classIds);
    List<Classes> list = this.list(qw);
    return list;
  }

  @Lazy
  @Autowired
  private UserService userService;
  @Override
  public List<User> getUserByClassesId(Long classId) {
    LambdaQueryWrapper<UserClass> qw = new LambdaQueryWrapper<>();
    qw.eq(UserClass::getClassId,classId);
    qw.select(UserClass::getUserId);
    List<UserClass> list = userClassService.list(qw);
    ArrayList<Long> ids = new ArrayList<>();
    for (UserClass userClass : list) {
      ids.add(userClass.getUserId());
    }
    if (ids.isEmpty()) {
      return new ArrayList<>();
    }
    List<User> userList = userService.listByIds(ids);
    return userList;
  }
}
