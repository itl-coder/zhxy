package com.example.zhxy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhxy.entity.pojo.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> getAllMenu();
}
