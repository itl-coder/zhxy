package com.example.zhxy.security.rememberme;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO 这个类不能被 Spring 容器管理
 * 自定义记住我 service 的实现，这个类必须实现它的构造方法
 */
@Slf4j
public class MyRememberServices extends PersistentTokenBasedRememberMeServices {
    public MyRememberServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }

    /**
     * 自定义前后端分离获取 remember-me 的方式
     *
     * @param request
     * @param parameter
     * @return
     */
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        log.info("parameter:{}", parameter);
        // 获取作用域中存储的
        String rememberMe = (String) request.getAttribute(parameter);
        if (!ObjectUtils.isEmpty(rememberMe)) {
            if (rememberMe.equalsIgnoreCase("true") || rememberMe.equalsIgnoreCase("on") || rememberMe.equalsIgnoreCase("yes") || rememberMe.equalsIgnoreCase("1")) {
                return true;
            }
        }
        this.logger.debug(LogMessage.format("Did not send remember-me cookie (principal did not set parameter '%s')", parameter));
        return false;
    }
}
