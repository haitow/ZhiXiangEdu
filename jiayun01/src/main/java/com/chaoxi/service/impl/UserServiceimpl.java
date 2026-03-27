package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.UserCardMapper;
import com.chaoxi.mapper.UserMapper;
import com.chaoxi.pojo.*;
import com.chaoxi.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service

public class UserServiceimpl
    extends ServiceImpl<UserMapper, User>
    implements UserService {
  @Autowired
  private UserCardService userCardService;

  @Override
  public List<UserCard> getUserCardsByUserId(Long userid) {
    LambdaQueryWrapper<UserCard> qw = new LambdaQueryWrapper<>();
    qw.eq(UserCard::getUserId,userid);
    List<UserCard> list = userCardService.list(qw);
    return list;
  }

  @Autowired
  private CardService cardService;
  @Override
  public boolean saveUserCardByCardId(Long cardid, User user) {
    UserCard userCard = new UserCard();
    userCard.setUserId(user.getId());
    Card card = cardService.getById(cardid);
    userCard.setCardName(card.getName());
    userCard.setCardId(card.getId());
    userCard.setType(card.getType());
    userCard.setStartDate(LocalDateTime.now());
    userCard.setEndDate(
        userCard.getStartDate()
            .plusDays(card.getDay())
    );
//    从A--->B,从card复制到userCard,
    BeanUtils.copyProperties(card,userCard,"status","id","createTime","updateTime","createUser","updateUser");

    userCard.setStatus(1);

    boolean b = userCardService.save(userCard);
    return b;
  }

  @Autowired
  private UserClassService userClassService;
  @Autowired
  private ClassesService classesService;

  @Override
  public List<Classes> getUserClassByUserId(Long userId) {
//    根据用户id查询用户课程中间表的信息
    LambdaQueryWrapper<UserClass> qw = new LambdaQueryWrapper<>();
    qw.select(UserClass::getClassId);
    qw.eq(UserClass::getUserId,userId);
    List<UserClass> userClasses = userClassService.list(qw);
//    封装classes ids 集合
    ArrayList<Long> ids = new ArrayList<>();
    for (UserClass userClass : userClasses) {
      ids.add(userClass.getClassId());
    }
//    根据ids集合查询课程详细信息
    List<Classes> list = classesService.listByIds(ids);
    return list;
  }


  @Override
  public List<Classes> getTeacherClassByTeacherId(Long tId) {
    LambdaQueryWrapper<Classes> qw = new LambdaQueryWrapper<>();
    qw.eq(Classes::getTeacherId,tId);
    List<Classes> list = classesService.list(qw);
    return list;
  }

  @Autowired
  private UserCardMapper userCardMapper;
  @Override
  public boolean getSelectedCard(Long userId, Long cardId) {
    LambdaQueryWrapper<UserCard> qw = new LambdaQueryWrapper<>();
    qw.eq(UserCard::getUserId,userId);
    qw.eq(UserCard::getCardId,cardId);
    qw.lt(UserCard::getEndDate,LocalDateTime.now());
    UserCard userCard = userCardMapper.selectOne(qw);
    if (userCard == null) {
      return false;
    }
    return true;
  }
}
