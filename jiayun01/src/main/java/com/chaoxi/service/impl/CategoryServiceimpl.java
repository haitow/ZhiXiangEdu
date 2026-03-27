package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.CategoryMapper;
import com.chaoxi.pojo.Category;
import com.chaoxi.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceimpl
    extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {
}
