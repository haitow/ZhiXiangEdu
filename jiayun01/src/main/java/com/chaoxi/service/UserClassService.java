package com.chaoxi.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoxi.pojo.User;
import com.chaoxi.pojo.UserClass;

import java.util.HashMap;
import java.util.List;


public interface UserClassService extends IService<UserClass> {

  Long getClassesTotalCount(Long userId);

  Long getMouthTotal(Long userId);

  HashMap<String,Long> getTeacherCount(Long userId);

}
