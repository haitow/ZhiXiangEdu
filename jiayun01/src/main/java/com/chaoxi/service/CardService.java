package com.chaoxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoxi.pojo.Card;
import com.chaoxi.pojo.User;

import java.util.List;

public interface CardService extends IService<Card> {

  List<User> getUserByCardId(Long cardId);
}

