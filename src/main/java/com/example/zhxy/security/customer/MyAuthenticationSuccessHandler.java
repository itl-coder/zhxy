package com.example.zhxy.security.customer;


import com.example.zhxy.common.ResponseUtil;
import com.example.zhxy.common.ResultModel;
import com.example.zhxy.entity.pojo.SysUser;
import com.example.zhxy.entity.pojo.User;
import com.example.zhxy.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 自定义认证成功后的处理
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final RedisTemplate redisTemplate;
    private final JwtUtils jwtUtils;
    private ObjectMapper objectMapper;

    @Autowired
    public MyAuthenticationSuccessHandler(RedisTemplate redisTemplate, JwtUtils jwtUtils, ObjectMapper objectMapper) {
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess.........................");
        //从认证信息中获取登录用户信息
        SysUser securityUser = (SysUser) authentication.getPrincipal();
        log.info("securityUser: {}", securityUser.toString());
        User user = securityUser.getUser();
        // 将用户序列化为 字符串
        String strUserInfo = objectMapper.writeValueAsString(user);
        //获取用户的权限信息
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) securityUser.getAuthorities();
        // collect 收集
        List<String> authList = authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
        //生成jwt
        String jwtToken = jwtUtils.createJwt(strUserInfo, authList);
        log.info("onAuthenticationSuccess jwtToken: {}", jwtToken);
        ResponseUtil.out(response, ResultModel.success(HttpStatus.OK.value(), "jwt生成成功", jwtToken));
        //将jwt放到redis,设置过期时间和jwt的过期时间
        redisTemplate.opsForValue().set("logintoken:" + jwtToken, objectMapper.writeValueAsString(authentication), 2, TimeUnit.HOURS);
    }
}
