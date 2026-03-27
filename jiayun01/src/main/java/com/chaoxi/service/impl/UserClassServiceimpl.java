package com.chaoxi.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaoxi.mapper.UserClassMapper;
import com.chaoxi.pojo.Classes;
import com.chaoxi.pojo.UserClass;
import com.chaoxi.service.ClassesService;
import com.chaoxi.service.UserClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class UserClassServiceimpl
    extends ServiceImpl<UserClassMapper, UserClass>
    implements UserClassService {
  //    根据老师Id查询总课时
  @Lazy
  @Autowired
  private ClassesService classesService;
  @Override
  public Long getClassesTotalCount(Long userId) {
    LambdaQueryWrapper<Classes> qw = new LambdaQueryWrapper<>();
    qw.eq(Classes::getTeacherId,userId);
    long count = classesService.count(qw);
    return count;
  }
  //  根据老师Id查询本月课时
  @Override
  public Long getMouthTotal(Long userId) {
    LambdaQueryWrapper<Classes> qw = new LambdaQueryWrapper<>();
    qw.eq(Classes::getTeacherId,userId);
//    获取当月1号
    LocalDateTime time1 = LocalDateTime.of(
        LocalDateTime.now().getYear(),
        LocalDateTime.now().getMonth(), 1,0,0
    );
//    获取当月最后一天
    LocalDateTime time2 = LocalDateTime.of(
        LocalDateTime.now().getYear(),
        LocalDateTime.now().getMonth(),
        LocalDateTime.now().getMonth().maxLength(),23,59,59
    );

    qw.between(Classes::getCreateTime,time1,time2);
    long count = classesService.count(qw);
    return count;
  }

  @Override
  public HashMap<String, Long> getTeacherCount(Long userId) {
//    从线程池中获取线程  执行结果
    CompletableFuture<Long> allCount = CompletableFuture.supplyAsync(() -> {
      return getClassesTotalCount(userId);
    });
    CompletableFuture<Long> mouthCount = CompletableFuture.supplyAsync(() -> {
      return getMouthTotal(userId);
    });
    // 等待所有线程执行完成，主线程在往下走
    CompletableFuture.allOf(allCount,mouthCount).join();

    HashMap<String, Long> map = null;
    try {
      map = new HashMap<>();
      map.put("allCount",allCount.get());
      map.put("mouthCount",mouthCount.get());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    return map;
  }
}
