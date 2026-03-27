package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.ClassRoomMapper;
import com.chaoxi.pojo.ClassRoom;
import com.chaoxi.service.ClassRoomService;
import org.springframework.stereotype.Service;

@Service
public class ClassRoomServiceimpl
    extends ServiceImpl<ClassRoomMapper, ClassRoom>
    implements ClassRoomService {
}
