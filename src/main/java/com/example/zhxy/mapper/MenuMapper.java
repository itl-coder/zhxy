package com.example.zhxy.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.zhxy.entity.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper  extends BaseMapper<Menu> {
    List<Menu> getAllMenu();
}
