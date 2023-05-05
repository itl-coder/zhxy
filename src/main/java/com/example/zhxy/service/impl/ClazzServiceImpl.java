package com.example.zhxy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.entity.pojo.Clazz;
import com.example.zhxy.mapper.ClazzMapper;
import com.example.zhxy.service.ClazzService;
import org.springframework.stereotype.Service;

@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public IPage<Clazz> getClazzByOpr(Page<Clazz> page, String clazzName) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        // 根据班级名称模糊查询
        queryWrapper.lambda()
                .like(!StringUtils.isEmpty(clazzName), Clazz::getClassName, clazzName);
        // 降序显示
        queryWrapper.orderByAsc("id");
        Page<Clazz> gradePage = baseMapper.selectPage(page, queryWrapper);
        return gradePage;
    }
}
