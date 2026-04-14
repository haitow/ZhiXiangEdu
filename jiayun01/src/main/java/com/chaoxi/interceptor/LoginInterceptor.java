package com.chaoxi.interceptor;

import com.alibaba.fastjson.JSON;
import com.chaoxi.common.BaseContext;
import com.chaoxi.common.JwtUtils;
import com.chaoxi.common.PathMatcher;
import com.chaoxi.common.R;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String requestURI = request.getRequestURI();
    boolean check = PathMatcher.check(requestURI);
    if(check){
      log.info("白名单路径放行：{}",requestURI);
      return true;
    }

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      return true;
    }

    String token = request.getHeader("Authorization");
    if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")){
      log.info("未携带有效Token：{}",requestURI);
      response.setStatus(401);
      response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
      return false;
    }

    token = token.substring(7);

    try {
      if (!jwtUtils.validateToken(token)){
        log.info("Token无效或已过期：{}",requestURI);
        response.setStatus(401);
        response.getWriter().write(JSON.toJSONString(R.error("TOKEN_EXPIRED")));
        return false;
      }

      String tokenType = jwtUtils.getTokenType(token);
      if (!"access".equals(tokenType)){
        log.info("非AccessToken类型，拒绝访问：{}",requestURI);
        response.setStatus(401);
        response.getWriter().write(JSON.toJSONString(R.error("INVALID_TOKEN_TYPE")));
        return false;
      }

      Long userId = jwtUtils.getUserIdFromToken(token);
      String userType = jwtUtils.getUserTypeFromToken(token);
      String redisKey = "refresh:" + userType + ":" + userId;
      Object storedRefreshToken = redisTemplate.opsForValue().get(redisKey);
      if (StringUtils.isEmpty(storedRefreshToken)){
        log.info("RefreshToken已失效，需要重新登录：{}",requestURI);
        response.setStatus(401);
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
      }

      BaseContext.setCurrentId(userId);
      log.info("JWT认证通过，用户ID：{}，路径：{}",userId,requestURI);
      return true;
    } catch (Exception e) {
      log.error("Token解析异常：{}",e.getMessage());
      response.setStatus(401);
      response.getWriter().write(JSON.toJSONString(R.error("TOKEN_EXPIRED")));
      return false;
    }
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    BaseContext.removeCurrentId();
  }
}
