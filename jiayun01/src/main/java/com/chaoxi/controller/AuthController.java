package com.chaoxi.controller;

import com.chaoxi.common.JwtUtils;
import com.chaoxi.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/refresh")
    public R refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (StringUtils.isEmpty(refreshToken)) {
            return R.error("RefreshToken不能为空");
        }

        try {
            if (!jwtUtils.validateToken(refreshToken)) {
                return R.error("RefreshToken已过期，请重新登录");
            }

            String tokenType = jwtUtils.getTokenType(refreshToken);
            if (!"refresh".equals(tokenType)) {
                return R.error("无效的Token类型");
            }

            Long userId = jwtUtils.getUserIdFromToken(refreshToken);
            String userType = jwtUtils.getUserTypeFromToken(refreshToken);
            String redisKey = "refresh:" + userType + ":" + userId;

            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else " +
                    "return 0 " +
                    "end";
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            Long result = (Long) redisTemplate.execute(redisScript,
                    Collections.singletonList(redisKey),
                    refreshToken);

            if (result == null || result == 0) {
                return R.error("RefreshToken已失效，请重新登录");
            }

            String newAccessToken = jwtUtils.generateAccessToken(userId, userType);
            String newRefreshToken = jwtUtils.generateRefreshToken(userId, userType);

            redisTemplate.opsForValue().set(
                    redisKey,
                    newRefreshToken,
                    7,
                    TimeUnit.DAYS
            );

            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("accessToken", newAccessToken);
            tokenMap.put("refreshToken", newRefreshToken);
            return R.success(tokenMap);
        } catch (Exception e) {
            log.error("刷新Token异常：{}", e.getMessage());
            return R.error("刷新Token失败，请重新登录");
        }
    }
}
