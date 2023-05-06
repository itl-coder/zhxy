package com.example.zhxy.service.impl;


import com.example.zhxy.entity.pojo.User;
import lombok.extern.slf4j.Slf4j;
import com.example.zhxy.mapper.UserMapper;
import com.example.zhxy.entity.pojo.User;
import com.example.zhxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public IPage<User> getAdminByOpr(Page<User> page, User user) {
        log.info("getAdminByOpr:{}", user.getUserType());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 根据班级名称模糊查询
        queryWrapper.lambda()
                .like(!StringUtils.isEmpty(user.getUsername()), User::getUsername, user.getUsername());
        if (!StringUtils.isEmpty(user.getUserType())) {
            queryWrapper.eq("userType", user.getUserType());
        }
        // 降序显示
        queryWrapper.orderByAsc("id");
        log.info("queryWrapper: {}", queryWrapper);
        Page<User> userPage = baseMapper.selectPage(page, queryWrapper);
        return userPage;
    }

    @Override
    public IPage<User> getUserByOpr(Page<User> page, User user) {
        log.info("getUserByOpr:{}", user.getUserType());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 查询具体的用户信息
        if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getUserType())) {
            queryWrapper
                    .like("username", user.getUsername())
                    .eq("userType", user.getUserType());
            Page<User> userPage = baseMapper.selectPage(page, queryWrapper);
            return userPage;
        }

        // 否则显示所有普通用户信息
        if (StringUtils.isEmpty(user.getUsername())) {
            queryWrapper.eq("userType", user.getUserType());
        }
        Page<User> userPage = baseMapper.selectPage(page, queryWrapper);
        return userPage;

    }

    @Override
    public IPage<User> getTeacherByOpr(Page<User> page, User user) {
        log.info("getTeacherByOpr:{}", user.getUserType());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 根据班级名称模糊查询
        if (!StringUtils.isEmpty(user.getUsername())) {
            queryWrapper.like("username", user.getUsername());
        }
        if (!StringUtils.isEmpty(String.valueOf(user.getUserType()))) {
            queryWrapper.eq("userType", user.getUserType());
        }
        // 降序显示
        queryWrapper.orderByAsc("id");
        log.info("queryWrapper: {}", queryWrapper);
        Page<User> userPage = baseMapper.selectPage(page, queryWrapper);
        return userPage;
    }
}
