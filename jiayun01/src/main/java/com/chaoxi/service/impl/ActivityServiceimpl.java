package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.ActivityMapper;
import com.chaoxi.pojo.Activity;
import com.chaoxi.service.ActivityService;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceimpl
    extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService {
}
