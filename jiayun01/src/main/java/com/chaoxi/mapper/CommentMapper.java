package com.chaoxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaoxi.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> getCommentsByShopId(@Param("shopId") Long shopId);
}
