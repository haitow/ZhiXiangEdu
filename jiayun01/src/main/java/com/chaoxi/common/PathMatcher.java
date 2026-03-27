package com.chaoxi.common;

import org.springframework.util.AntPathMatcher;

public class PathMatcher {

    //定义不需要处理的请求路径
    public static String[] URLS = new String[]{
            "/back/**",
            "/user/sendMsg",
            "/user/login",
            "/admin/login",
            "/alipay/**",
            "/common/download"
    };
    /*采用默认*/
    public static boolean check(String requestURI){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String url : URLS) {
            boolean match = antPathMatcher.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }

    /**
     * 参数匹配器
     * @param urls 名单数组
     * @param requestURI 需要验证的网址
     * @return
     * 例如：
     *  /a/*匹配a前缀一层目录
     *  /a/** 匹配a前缀多级目录
     */
    public static boolean check(String[] urls,String requestURI){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String url : urls) {
            boolean match = antPathMatcher.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }

}
