package com.example.zhxy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.entity.pojo.Grade;
import com.example.zhxy.mapper.GradeMapper;
import com.example.zhxy.service.GradeService;
import org.springframework.stereotype.Service;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        // 根据班级名称模糊查询
        queryWrapper.lambda()
                .like(!StringUtils.isEmpty(gradeName), Grade::getGradeName, gradeName);
        // 降序显示
        queryWrapper.orderByAsc("gradeId");
        Page<Grade> gradePage = baseMapper.selectPage(page, queryWrapper);
        return gradePage;
    }
}
