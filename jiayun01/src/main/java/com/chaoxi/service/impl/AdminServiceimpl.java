package com.chaoxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.AdminMapper;
import com.chaoxi.pojo.Admin;
import com.chaoxi.service.AdminService;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceimpl
    extends ServiceImpl<AdminMapper, Admin>
    implements AdminService {
}
