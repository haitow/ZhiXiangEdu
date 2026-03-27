package com.chaoxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaoxi.common.R;
import com.chaoxi.pojo.Card;
import com.chaoxi.pojo.User;
import com.chaoxi.pojo.UserCard;
import com.chaoxi.service.CardService;
import com.chaoxi.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/card")
public class CardController {
  @Autowired
  private CardService cardService;

//  分页查询
  @GetMapping("/page")
  public R page(Integer page,Integer pageSize,String name){
    Page<Card> cardPage = new Page<>(page, pageSize);
    LambdaQueryWrapper<Card> qw = new LambdaQueryWrapper<>();
    qw.like(!StringUtils.isEmpty(name),Card::getName,name);
    qw.orderByDesc(Card::getCreateTime);
    qw.orderByAsc(Card::getName);
    cardService.page(cardPage,qw);
    return R.success(cardPage);
  }

//新增card查询所有
  @PostMapping
  public R save(@RequestBody Card card){
    card.setStatus(1);
    return cardService.save(card) ? R.success("新增成功"):R.error("新增失败");
  }

  //修改card
  @GetMapping("/{id}")
  public R getCardById(@PathVariable Long id){
    Card card = cardService.getById(id);
    return R.success(card);
  }
  @PutMapping
  public R update(@RequestBody Card card){
    return cardService.updateById(card) ? R.success("修改成功"):R.error("修改失败");
  }


  //  停用操作
  @PutMapping("/change")
  public R change(@RequestBody Card card){
    boolean b = cardService.updateById(card);
    return b? R.success("修改成功"): R.error("修改失败");
  }

//  删除
  @DeleteMapping
  public R deleteById(@RequestParam Long id){
    boolean b = cardService.removeById(id);
    return b? R.success("删除成功"): R.error("删除失败");
  }

//  根据卡的id查询拥有此卡的用户
  @GetMapping("/user/{cardId}")
  public R getUserByCardId(@PathVariable Long cardId){
    List<User> userList = cardService.getUserByCardId(cardId);
    return R.success(userList);
  }

//  小程序查卡
  @GetMapping("/getCardList")
  public R findCard(@RequestParam(required = false) String type){
    LambdaQueryWrapper<Card> qw = new LambdaQueryWrapper<>();
    qw.eq(!StringUtils.isEmpty(type),Card::getType,type);
    qw.orderByDesc(Card::getUpdateTime);
    List<Card> list = cardService.list(qw);
    return R.success(list);
  }
}
