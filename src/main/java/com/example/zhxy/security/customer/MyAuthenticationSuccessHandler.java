package com.example.zhxy.security.customer;


import cn.hutool.core.bean.BeanUtil;
import com.example.zhxy.common.ResponseUtil;
import com.example.zhxy.common.ResultModel;
import com.example.zhxy.entity.pojo.SysUser;
import com.example.zhxy.entity.pojo.User;
import com.example.zhxy.entity.vo.LoginVO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        log.info("user: {}", user.toString());
        // 将用户序列化为 字符串
        String strUserInfo = objectMapper.writeValueAsString(user);
        //获取用户的权限信息
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) securityUser.getAuthorities();
        // collect 收集
        List<String> authList = authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
        //生成jwt
        String jwtToken = jwtUtils.createJwt(strUserInfo, authList);
        log.info("onAuthenticationSuccess jwtToken: {}", jwtToken);
        Map<String, Object> map = new HashMap<>();
        // 使用属性拷贝工具类将User对象的属性值拷贝到LoginVO对象中
        map.put("jwtToken", jwtToken);
        LoginVO loginVO = new LoginVO();

        loginVO = copyUserTologinVo(user, loginVO);
        map.put("userInfo", loginVO);
        // 将 map 序列化为 json 字符串
        ResponseUtil.out(response, ResultModel.success(HttpStatus.OK.value(), "登陆成功", map));
        //将jwt放到redis,设置过期时间和jwt的过期时间
        redisTemplate.opsForValue().set("logintoken:" + jwtToken, objectMapper.writeValueAsString(authentication), 2, TimeUnit.HOURS);
    }

    private LoginVO copyUserTologinVo(User user, LoginVO loginVO) {
        log.info("into copyUserTologinVo................");
        // 调用了 BeanUtil.toBean方法，将User对象转换成LoginVO对象
        loginVO = BeanUtil.toBean(user, LoginVO.class);
        return loginVO;
    }


}
