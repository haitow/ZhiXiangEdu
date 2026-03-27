package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.TagsMapper;
import com.chaoxi.pojo.Tags;
import com.chaoxi.service.TagsService;
import org.springframework.stereotype.Service;

@Service
public class TagsServiceimpl
    extends ServiceImpl<TagsMapper, Tags>
    implements TagsService {
}
