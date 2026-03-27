package com.chaoxi.service.impl;


import  com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.CardMapper;
import com.chaoxi.pojo.Card;
import com.chaoxi.pojo.User;
import com.chaoxi.pojo.UserCard;
import com.chaoxi.service.CardService;
import com.chaoxi.service.UserCardService;
import com.chaoxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceimpl
    extends ServiceImpl<CardMapper, Card>
    implements CardService {

  @Autowired
  private UserCardService userCardService;

  @Lazy
  @Autowired
  private UserService userService;

  @Override
  public List<User> getUserByCardId(Long cardId) {
    LambdaQueryWrapper<UserCard> qw = new LambdaQueryWrapper<>();
    qw.select(UserCard::getUserId);
    qw.eq(UserCard::getCardId,cardId);
    List<UserCard> list = userCardService.list(qw);
    ArrayList<Long> ids = new ArrayList<>();
    for (UserCard userCard : list) {
      ids.add(userCard.getUserId());
    }
    List<User> idsList = userService.listByIds(ids);
    return idsList;
  }
}
