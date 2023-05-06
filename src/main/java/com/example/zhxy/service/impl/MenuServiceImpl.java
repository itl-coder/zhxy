package com.example.zhxy.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.entity.pojo.Menu;
import com.example.zhxy.mapper.MenuMapper;
import com.example.zhxy.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }
}
