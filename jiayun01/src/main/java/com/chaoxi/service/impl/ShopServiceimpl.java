package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.ShopMapper;
import com.chaoxi.pojo.Shop;
import com.chaoxi.service.ShopService;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceimpl
    extends ServiceImpl<ShopMapper,Shop>
    implements ShopService {
}
