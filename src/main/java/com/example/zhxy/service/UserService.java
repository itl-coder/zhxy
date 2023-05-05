package com.example.zhxy.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhxy.entity.pojo.User;

public interface UserService extends IService<User> {
    IPage<User> getAdminByOpr(Page<User> page, User user);

    IPage<User> getUserByOpr(Page<User> page, User user);

    IPage<User> getTeacherByOpr(Page<User> page, User user);
}
