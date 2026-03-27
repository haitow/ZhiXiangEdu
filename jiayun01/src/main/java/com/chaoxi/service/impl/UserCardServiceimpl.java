package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.UserCardMapper;
import com.chaoxi.pojo.UserCard;
import com.chaoxi.service.UserCardService;
import org.springframework.stereotype.Service;

@Service
public class UserCardServiceimpl
    extends ServiceImpl<UserCardMapper, UserCard>
    implements UserCardService {
}
