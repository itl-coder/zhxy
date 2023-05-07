package com.example.zhxy.filter;


import com.example.zhxy.common.ResponseUtil;
import com.example.zhxy.common.ResultModel;
import com.example.zhxy.entity.pojo.User;
import com.example.zhxy.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtCheckFilter extends OncePerRequestFilter {
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        // 如果是登录请求urI，直接放行
        if (requestURI.equals("/login") || requestURI.equals("/common/captcha") || requestURI.equals("/download") || requestURI.equals("/upload/headerImgUpload") ) {
            log.info("放行该请求.................");
            doFilter(request, response, filterChain);
            return;
        }

        String strAuth = request.getHeader("Authorization");
        if (StringUtils.isEmpty(strAuth)) {
            log.error("Authorization为空.............");
            ResponseUtil.out(response, ResultModel.error(HttpStatus.UNAUTHORIZED.value(), "Authorization 为空".toString()));
            return;
        }
        String jwtToken = strAuth.replace("bearer ", "");
        if (StringUtils.containsWhitespace(jwtToken)) {
            log.error("jwt为空............");
            ResponseUtil.out(response, ResultModel.error(HttpStatus.UNAUTHORIZED.value(), "jwt 为空".toString()));
            return;
        }

        //校验jwt
        boolean verifyResult = jwtUtils.verifyToken(jwtToken);
        if (!verifyResult) {
            log.error("jwt非法....................");
            ResponseUtil.out(response, ResultModel.error(HttpStatus.UNAUTHORIZED.value(), "jwt非法！！！！".toString()));
            return;
        }

        //判断redis是否存在jwt
        String redisToken = (String)redisTemplate.opsForValue().get("logintoken:" + jwtToken);
        if (StringUtils.isEmpty(redisToken)) {
            ResponseUtil.out(response, ResultModel.error(HttpStatus.OK.value(), "您已经退出，请重新登录！！！！".toString()));
            return;
        }
        // 从 jwt 里获取用户信息和权限信息
        //从jwt里获取用户信息和权限信息
        String userInfo = jwtUtils.getUserInfoFromToken(jwtToken);
        List<String> userAuthList = jwtUtils.getUserAuthFromToken(jwtToken);
        //反序列化成 User 对象
        User user = objectMapper.readValue(userInfo, User.class);
        List<SimpleGrantedAuthority> authorityList=userAuthList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        //用户名密码认证token
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(user,null,authorityList);
        // 把 token 放到安全上下文：securityContext
        SecurityContextHolder.getContext().setAuthentication(token);
        doFilter(request,response,filterChain); // 放行
    }
}