package com.chaoxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chaoxi.pojo.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {


  List<Comment> getCommentsByShopId(Long shopId);

}
