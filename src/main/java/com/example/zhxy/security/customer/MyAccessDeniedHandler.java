package com.example.zhxy.security.customer;

import com.example.zhxy.common.ResponseUtil;
import com.example.zhxy.common.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义授权异常处理
 */
@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("权限拒绝: {}", accessDeniedException.getMessage().toString());
        ResponseUtil.out(response, ResultModel.error(HttpStatus.UNAUTHORIZED.value(), accessDeniedException.getMessage().toString()));
    }
}
