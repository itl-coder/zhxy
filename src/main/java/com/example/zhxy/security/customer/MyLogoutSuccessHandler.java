package com.example.zhxy.security.customer;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.zhxy.common.ResponseUtil;
import com.example.zhxy.common.ResultModel;
import com.example.zhxy.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义注销成功的处理
 */
@Slf4j
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    private final RedisTemplate redisTemplate;
    private final JwtUtils jwtUtils;

    @Autowired
    public MyLogoutSuccessHandler(RedisTemplate redisTemplate, JwtUtils jwtUtils) {
        this.redisTemplate = redisTemplate;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String auth = request.getHeader("Authorization");
        if (StringUtils.isEmpty(auth)) {
            ResponseUtil.out(response, ResultModel.error(HttpStatus.FORBIDDEN.value(), "jwt 不存在", ""));
            return;
        }
        String jwtToken = auth.replace("bearer ", "");
        boolean result = jwtUtils.verifyToken(jwtToken);
        if (!result) {
            ResponseUtil.out(response, ResultModel.error(HttpStatus.FORBIDDEN.value(), "jwt 非法", ""));
            return;
        }
        //从redis中删除登录成功后放入的jwttoken
        redisTemplate.delete("logintoken:" + jwtToken);
        log.info("jwtToken: {}", jwtToken);
        ResponseUtil.out(response, ResultModel.success(HttpStatus.OK.value(), "退出成功", ""));

    }
}
