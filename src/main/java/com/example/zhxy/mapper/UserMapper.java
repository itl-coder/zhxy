package com.example.zhxy.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.zhxy.entity.pojo.Role;
import com.example.zhxy.entity.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    // 根据用户名查询用户
    User findUserByUserName(@Param("username") String username);

    // 根据用户id查询角色集合
    List<Role> getRoleByUid(Integer uid);

    // 自动密码更新
    Integer updatePassword(String username, @Param("password") String newPassword);
}
