package com.chaoxi.controller;

import com.chaoxi.common.R;
import com.chaoxi.pojo.Comment;
import com.chaoxi.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {
  @Autowired
  private CommentService commentService;

  @GetMapping("/getCommentsByShopId/{shopId}")
  public R getCommentsByShopId(@PathVariable Long shopId){
    List<Comment> list = commentService.getCommentsByShopId(shopId);
    return R.success(list);
  }
}
