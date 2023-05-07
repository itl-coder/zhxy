package com.example.zhxy.security.config;

import com.example.zhxy.filter.JwtCheckFilter;
import com.example.zhxy.filter.LoginFilter;
import com.example.zhxy.security.customer.*;
import com.example.zhxy.security.metasource.CustomSecurityMetadataSource;
import com.example.zhxy.security.rememberme.MyRememberServices;
import com.example.zhxy.service.impl.MyUserDetailService;
import com.example.zhxy.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.sql.DataSource;
import java.util.UUID;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtCheckFilter jwtCheckFilter;
    // RememberMe 需要的数据源
    private final DataSource dataSource;
    // Jwt
    private final JwtUtils tokenManager;
    // redis
    private final RedisTemplate redisTemplate;
    // 数据库数据源认证
    private final MyUserDetailService myUserDetailService;
    // 自定义授权异常处理
    private final MyAccessDeniedHandler myAccessDeniedHandler;
    // 登录成功处理
    private final MyLogoutSuccessHandler myLogoutSuccessHandler;
    // 自定义认证异常处理
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    // 元数据信息集合
    private final CustomSecurityMetadataSource customSecurityMetadataSource;
    // 自定义认证成功处理
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    // 自定义认证失败处理
    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    public SecurityConfig(DataSource dataSource, JwtUtils tokenManager, RedisTemplate redisTemplate, JwtCheckFilter jwtCheckFilter, MyUserDetailService myUserDetailService, MyAccessDeniedHandler myAccessDeniedHandler, MyLogoutSuccessHandler myLogoutSuccessHandler, MyAuthenticationEntryPoint myAuthenticationEntryPoint, CustomSecurityMetadataSource customSecurityMetadataSource, MyAuthenticationFailureHandler myAuthenticationFailureHandler, MyAuthenticationSuccessHandler myAuthenticationSuccessHandler) {
        this.dataSource = dataSource;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.jwtCheckFilter = jwtCheckFilter;
        this.myUserDetailService = myUserDetailService;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
        this.myLogoutSuccessHandler = myLogoutSuccessHandler;
        this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
        this.customSecurityMetadataSource = customSecurityMetadataSource;
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
    }

    // 放行资源白名单
    private String[] WHITE = {"/error", "/doc.html", "/css/**", "/img/**", "/druid/**", "/common/**", "/webjars/**", "/*/api-docs", "/swagger-ui.html", "/swagger-resources/**"};


    /**
     * TODO: 自定义前后端分离 Form 表单 => JSON 格式
     * 自定义 Filter 交给工厂管理
     */
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter(redisTemplate);
        // 设置认证路径
        loginFilter.setFilterProcessesUrl("/login");
        // 指定接受 json 用户名的 key
        loginFilter.setUsernameParameter("username");
        // 指定接受 json 密码的 key
        loginFilter.setPasswordParameter("password");
        // 指定接受 json 验证码的 key
        loginFilter.setKaptchaParameter("kaptcha");
        // 指定接受 json 记住我的 key
        loginFilter.setRememberMeParameter("remember-me");
        // TODO 什么作用
        loginFilter.setAuthenticationManager(authenticationManager());
        // 认账成功处理
        loginFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        // 认证失败处理
        loginFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        loginFilter.setRememberMeServices(rememberMeServices());
        return loginFilter;
    }

    /**
     * authenticationManagerBean 是一个方法名，用于获取一个 Spring Security 的认证管理器实例，
     * 该方法将认证管理器实例化并将其注入到 Spring 上下文中以供其他 Bean 使用。
     * Spring Security 默认会为您提供一个认证管理器实例，但如果您需要在自己的代码中使用它，
     * 可以使用这个方法将其注入到您的代码中。
     * 在这个方法中，super.authenticationManagerBean() 调用了父类的同名方法，
     * 返回了一个 AuthenticationManager 实例。这个实例将被 Spring 管理并注入到上下文中。
     * Regenerate response
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailService);
        ProviderManager pm = new ProviderManager(daoAuthenticationProvider);
        return pm;
    }

    /**
     * 自定义 AuthenticationManager 推荐
     * 它的作用是管理用户认证的过程。
     * 具体来说，它接收用户的登录请求并从Spring Security进行用户认证。在进行用户认证的过程中，AuthenticationManager
     * 首先根据用户名获取用户信息，
     * 然后将给定的用户名和密码与用户信息进行比较，如果验证通过，则认为用户已经被认证。如果验证失败，则会抛出异常，表示用户认证失败。
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }

    /**
     * 放行的资源不经过过滤器安全链
     * LOG: Will not secure Ant [pattern='/img/**']
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(false).ignoring().antMatchers(WHITE);
    }


    /**
     * anyRequest | 匹配所有请求路径
     * access | SpringEl表达式结果为true时可以访问
     * anonymous | 匿名可以访问
     * denyAll | 用户不能访问
     * fullyAuthenticated | 用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority | 如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole | 如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority | 如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress | 如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole | 如果有参数，参数表示角色，则其角色可以访问
     * permitAll | 用户可以任意访问
     * rememberMe | 允许通过remember-me登录的用户访问
     * authenticated | 用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 添加 CORS
        http.cors();

        // 获取工厂对象
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        // 设置自定义 url 权限处理
        http.apply(new UrlAuthorizationConfigurer<>(applicationContext)).withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(customSecurityMetadataSource);
                // 是否拒绝公共资源访问 比如访问公共的 验证码(允许访问: false)
                object.setRejectPublicInvocations(false);
                return object;
            }
        });

        http.formLogin().and().authorizeRequests()
                // 放行预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 注意: /login 不应出现在非安全的 web 放行资源配置中
                .mvcMatchers("/login", "/logout").permitAll()
                // 其他所有请求需要认证
                .anyRequest().authenticated();

        http
                // CSRF 禁用，因为不使用 session
                .csrf().disable()
                // 禁用 HTTP 响应标头
                .headers().cacheControl().disable().and()
                // 认证异常的处理
                .exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
                // 授权异常处理
                .accessDeniedHandler(myAccessDeniedHandler).and()
                // 基于 token，所以不需要 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers().frameOptions().disable();

        // 注销
        http.logout() // 设置为表达式处理器
                // 前后端分离的处理方式，页面不跳转,响应 json 格式
                .logoutSuccessHandler(myLogoutSuccessHandler)
                // 清除会话、清楚认证标记、注销成功后的默认跳转到登录页等为默认配置,可以不声明出现
                // 退出的请求方式指定 GET、POST
                .logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/logout", "POST"),
                        // 可以指定多种同时指定请求方式
                        new AntPathRequestMatcher("/myLogout", "POST")));

        // 记住我
        http.rememberMe().rememberMeServices(rememberMeServices())
                // TODO 前后端分离的实现: 设置自动登录使用那个 rememberMe 第二次设置的作用: 解码
                .tokenValiditySeconds(3600);
        // at: 用来某个 filter 替换过滤器链中那个 filter
        // before: 放在过滤器链中那个 filter 之前
        // after: 放在过滤器链中那个 filter 之后
        // 添加 JWT 验证
        http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 点击 JdbcTokenRepositoryImpl => 获取创建表结构的 SQL
     * public static final String CREATE_TABLE_SQL = "
     * create table persistent_logins (
     * username varchar(64) not null,
     * series varchar(64) primary key,
     * token varchar(64) not null,
     * last_used timestamp not null)
     * ";
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    /**
     * 前后端分离记住我的实现
     *
     * @return MyRememberServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository)
     */
    @Bean
    public RememberMeServices rememberMeServices() {
        return new MyRememberServices(UUID.randomUUID().toString(), userDetailsService(), persistentTokenRepository());
    }
}
