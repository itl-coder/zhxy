package com.example.zhxy.security.customer;


import com.example.zhxy.common.ResponseUtil;
import com.example.zhxy.common.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败的处理
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("认证失败: {}", exception.getMessage().toString());
        ResponseUtil.out(response, ResultModel.error(HttpStatus.FORBIDDEN.value(), exception.getMessage().toString()));
    }
}
