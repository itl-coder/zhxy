package com.example.zhxy.security.customer;


import com.example.zhxy.common.ResponseUtil;
import com.example.zhxy.common.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("authException: {}", authException.getMessage().toString());
        ResponseUtil.out(response, ResultModel.error(HttpStatus.FORBIDDEN.value(), authException.getMessage().toString()));
    }
}
