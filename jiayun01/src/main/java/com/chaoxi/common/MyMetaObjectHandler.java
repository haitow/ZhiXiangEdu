package com.chaoxi.common;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
  /**
   * 插入操作，自动填充
   * @param metaObject
   */
  @Override
  public void insertFill(MetaObject metaObject) {
    log.info("公共字段自动填充[insert]...");

    metaObject.setValue("createTime", LocalDateTime.now());
    metaObject.setValue("updateTime",LocalDateTime.now());
    Long userid = BaseContext.getCurrentId();
    System.err.println("admin"+userid);
    metaObject.setValue("createUser",userid);
    metaObject.setValue("updateUser",userid);
  }
  /**
   * 更新操作，自动填充
   * @param metaObject
   */
  @Override
  public void updateFill(MetaObject metaObject) {
    log.info("公共字段自动填充[update]...");
    metaObject.setValue("updateTime",LocalDateTime.now());
    Long userid = (Long) BaseContext.getCurrentId();
    metaObject.setValue("updateUser",userid);
  }
}
