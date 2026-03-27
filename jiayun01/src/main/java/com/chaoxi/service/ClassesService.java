package com.chaoxi.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoxi.pojo.Classes;
import com.chaoxi.pojo.User;

import java.util.List;

public interface ClassesService extends IService<Classes> {
  List<String> getClassByCardTags(String tags);
  List<Classes> getClassListByTags(String tags);

  List<User> getUserByClassesId(Long classId);

}
