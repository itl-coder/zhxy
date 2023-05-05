package com.example.zhxy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.zhxy.entity.pojo.Role;
import com.example.zhxy.entity.pojo.SysUser;
import com.example.zhxy.entity.pojo.User;
import com.example.zhxy.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class MyUserDetailService implements UserDetailsService, UserDetailsPasswordService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询用户
        User user = userMapper.findUserByUserName(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 2. 查询用户的权限信息
        // 查询角色信息
        List<Role> roles = userMapper.getRoleByUid(user.getId());
        user.setRoles(roles);
        return new SysUser(user);
    }

    /**
     * 自动密码升级解决方案 {推荐: 随着 SpringSecurity 版本的升级,密码的底层加密会实现自动升级}
     *
     * @param user
     * @param newPassword
     * @return
     */
    // 实现密码更新
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        log.info("updatePassword: {}", user.getUsername());
        User u = userMapper.findUserByUserName(user.getUsername());
        if (u == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        u.setPassword(newPassword);
        SysUser sysUser = new SysUser(u);
        userMapper.updatePassword(user.getUsername(), sysUser.getPassword());
        return sysUser;
    }
}
