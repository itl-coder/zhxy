package com.example.zhxy.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhxy.entity.pojo.Clazz;

public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> getClazzByOpr(Page<Clazz> page, String clazzName);
}
