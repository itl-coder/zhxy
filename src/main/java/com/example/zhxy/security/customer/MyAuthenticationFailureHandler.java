package com.example.zhxy.security.customer;


import com.example.zhxy.common.ResponseUtil;
import com.example.zhxy.common.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
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
        if (exception instanceof LockedException) {
            ResponseUtil.out(response, ResultModel.error(HttpStatus.FORBIDDEN.value(), "账户被锁定,请联系管理员!"));
        } else if (exception instanceof CredentialsExpiredException) {
            ResponseUtil.out(response, ResultModel.error(HttpStatus.FORBIDDEN.value(), "密码过期,请联系管理员!"));
        } else if (exception instanceof DisabledException) {
            ResponseUtil.out(response, ResultModel.error(HttpStatus.FORBIDDEN.value(), "账户被禁用,请联系管理员!"));
        } else if (exception instanceof BadCredentialsException) {
            ResponseUtil.out(response, ResultModel.error(HttpStatus.FORBIDDEN.value(), "用户名或密码输入错误,请联系管理员!"));
        }
        ResponseUtil.out(response, ResultModel.error(HttpStatus.FORBIDDEN.value(), exception.getMessage().toString()));
    }
}
