package com.example.zhxy.exception;


import com.example.zhxy.common.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle extends RuntimeException {

    @ExceptionHandler(value = {Exception.class})
    protected ResultModel handleAllException(Exception ex) {
        return ResultModel.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage().toString());
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResultModel handleNotFound(Exception ex) {
        log.error("错误信息: {}", ex.getMessage().toString());
        return ResultModel.error(HttpStatus.NOT_FOUND.value(), ex.getMessage().toString());
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResultModel handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return ResultModel.error(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultModel handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                           HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return ResultModel.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage().toString());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultModel handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return ResultModel.error(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResultModel handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResultModel.error(ex.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResultModel.error(message);
    }

}
