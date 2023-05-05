package com.example.zhxy.filter;


import com.example.zhxy.exception.KaptchaNotMatchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义前后端分离认证 Filter
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private RedisTemplate redisTemplate;

    public LoginFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 设置默认的表单验证码 name = kaptcha
    private static final String FORM_KAPTCHA_KEY = "kaptcha";
    private static final String FORM_REMEMBER_ME_KEY = "remember-me";

    private String kaptchaParameter = FORM_KAPTCHA_KEY;
    private String rememberMeParameter = FORM_REMEMBER_ME_KEY;

    // 提供自定义的验证码名称
    public String getKaptchaParameter() {
        return this.kaptchaParameter;
    }

    public void setKaptchaParameter(final String kaptchaParameter) {
        this.kaptchaParameter = kaptchaParameter;
    }

    public String getRememberMeParameter() {
        return rememberMeParameter;
    }

    public void setRememberMeParameter(String rememberMeParameter) {
        this.rememberMeParameter = rememberMeParameter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // 1. 判断请求方式是否是 POST
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        // 2. 判断 数据是否是 JSON 格式
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            try {
                log.info("into login filter............ ");
                // 将请求体中的数据进行反序列化
                Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), HashMap.class);
                // 获取 json 用户名
                String username = userInfo.get(getUsernameParameter());
                // 获取 json 密码
                String password = userInfo.get(getPasswordParameter());
                // 获取 json 验证码
                String kaptcha = userInfo.get(getKaptchaParameter());
                // 获取 session 中的验证码
                String redisCode = (String) redisTemplate.opsForValue().get("kaptcha");
                log.info("redisCode: {}", redisCode);
                // 获取 json 中的记住我
                String rememberMe = userInfo.get(getRememberMeParameter());
                // 判断是否传递 remember-me
                if (!ObjectUtils.isEmpty(rememberMe)) {
                    // 将这个 remember-me 设置到作用域中
                    request.setAttribute(getRememberMeParameter(), rememberMe);
                }
                // 用户输入的验证码和 session 作用域中的都不能为空
                if (!ObjectUtils.isEmpty(kaptcha) && !ObjectUtils.isEmpty(redisCode) && kaptcha.equalsIgnoreCase(redisCode)) {
                    log.info("用户名: {} 密码: {},是否记住我: {}", userInfo, password, rememberMe);
                    // 获取用户名和密码认证
                    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                    setDetails(request, authRequest);
                    return this.getAuthenticationManager().authenticate(authRequest);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 没有通过则执行自定义异常
            throw new KaptchaNotMatchException("验证码不匹配!");
        }
        // 如果不是 JSON 格式数据,则调用传统方式进行认证
        return super.attemptAuthentication(request, response);
    }
}
