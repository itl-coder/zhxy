package com.example.zhxy.security.metasource;


import com.example.zhxy.entity.pojo.Menu;
import com.example.zhxy.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final MenuService menuService;

    @Autowired
    public CustomSecurityMetadataSource(MenuService menuService) {
        this.menuService = menuService;
    }

    // 用于路径匹配
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 构建元数据信息集合
     * /admin/**    ROLE_ADMIN
     * /teacher/**  ROLE_TEACHER
     * /student/**  ROLE_TEACHER ROLE_USER
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 1. 获取当前请求对象
        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
        // 2. 查询所有菜单
        List<Menu> allMenu = menuService.getAllMenu();
        log.info("allMenuL {}", allMenu.toString());
        for (Menu menu : allMenu) {
            if (antPathMatcher.match(menu.getPattern(), requestURI)) {
                String[] roles = menu.getRoles().stream().map(r -> r.getName()).toArray(String[]::new);
                log.info("roles: {}", roles);
                return SecurityConfig.createList(roles);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
