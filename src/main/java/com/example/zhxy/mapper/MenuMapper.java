package com.example.zhxy.mapper;


import com.example.zhxy.entity.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {
    List<Menu> getAllMenu();
}
