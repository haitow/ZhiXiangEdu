package com.chaoxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.CommentMapper;
import com.chaoxi.pojo.Comment;
import com.chaoxi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceimpl
    extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {
  @Autowired
  private CommentMapper commentMapper;

  @Override
  public List<Comment> getCommentsByShopId(Long shopId) {
    List<Comment> list = commentMapper.getCommentsByShopId(shopId);
    return list;
  }
}
