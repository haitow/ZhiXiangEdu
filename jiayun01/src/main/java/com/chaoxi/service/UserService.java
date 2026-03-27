package com.chaoxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoxi.pojo.Classes;
import com.chaoxi.pojo.User;
import com.chaoxi.pojo.UserCard;

import java.util.List;

public interface UserService extends IService<User> {
  List<UserCard> getUserCardsByUserId(Long userid);
  boolean saveUserCardByCardId(Long cardid, User user);

  List<Classes> getUserClassByUserId(Long userId);

  List<Classes> getTeacherClassByTeacherId(Long tId);

  boolean getSelectedCard(Long userId, Long cardId);

}
