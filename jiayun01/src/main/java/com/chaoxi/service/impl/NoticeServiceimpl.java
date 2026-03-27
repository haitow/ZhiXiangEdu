package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.NoticeMapper;
import com.chaoxi.pojo.Notice;
import com.chaoxi.service.NoticeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceimpl
    extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService {
}
