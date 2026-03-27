package com.chaoxi.interceptor;

import com.alibaba.fastjson.JSON;
import com.chaoxi.common.BaseContext;
import com.chaoxi.common.PathMatcher;
import com.chaoxi.common.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// 配置拦截
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
  @Autowired
  private RedisTemplate redisTemplate;
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 1. 白名单资源放行
    // 用户的访问路径
    String requestURI = request.getRequestURI();
    boolean check = PathMatcher.check(requestURI);// true 放行  false 拦截
    if(check){
      log.info("白名单路径放行：{}",requestURI);
      return true;
    }

    // 2. 已登录放行
//    HttpSession session = request.getSession();
//    Object user = session.getAttribute("user");

//    从请求头auth中获取id
    String auth = request.getHeader("auth");
//    根据authxxxxxxxxxxxxxxx去redis中查询
    Object userId = null;
    if (!StringUtils.isEmpty(auth)){
      userId = redisTemplate.opsForValue().get("auth"+auth);
    }
//    根据从redis中获得的id进行判断
    if(!StringUtils.isEmpty(userId)){
      log.info("已登录放行：{}",requestURI);

      // 把用户ID 存入线程空间
      BaseContext.setCurrentId((Long) userId);
//      System.err.println("拦截器："+Thread.currentThread().getId());

      return true;
    }
    // 权限不够 。。。
    // 3. 拦截并响应信息
    response.setStatus(401);// 设置状态码401
    response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    return false;
  }





  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}

