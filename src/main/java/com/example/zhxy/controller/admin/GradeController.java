package com.example.zhxy.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.common.ResultModel;
import com.example.zhxy.entity.pojo.Grade;
import com.example.zhxy.service.GradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "年级管理模块")
@RestController
@RequestMapping("/admin/getGrades")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    /**
     * 实现带有分页功能的班级管理
     *
     * @param pageNo    当前页
     * @param pageSize  每页大小
     * @param gradeName 班级名称
     * @return
     */
    @ApiOperation("班级分页查询")
    @GetMapping("/{pageNo}/{pageSize}")
    public ResultModel getGrades(
            @ApiParam(value = "当前页", required = true) @PathVariable("pageNo") Integer pageNo,
            @ApiParam(value = "每页大小", required = true) @PathVariable("pageSize") Integer pageSize,
            // @RequestParam("gradeName")  前后参数保持一致时可以省略
            @ApiParam(value = "班级名称") @RequestParam("gradeName") String gradeName) {
        Page<Grade> page = new Page<>(pageNo, pageSize);
        // 通过服务层查询
        IPage<Grade> pageGrade = gradeService.getGradeByOpr(page, gradeName);
        return ResultModel.success(pageGrade);
    }

    /**
     * 实现更新或添加班级信息
     *
     * @return
     */
    @ApiOperation("更新或添加")
    @PostMapping("/saveOrUpdateGrade")
    public ResultModel saveOrUpdateGrade(
            @ApiParam("班级信息") @RequestBody Grade grade) {
        boolean saveOrUpdate = gradeService.saveOrUpdate(grade);
        if (saveOrUpdate) {
            return ResultModel.success("操作成功!");
        }
        return ResultModel.error("操作失败!");
    }

    @ApiOperation("根据ID删除一个")
    @GetMapping("/remove/{gradeId}")
    public ResultModel saveOrUpdateGrade(
            @ApiParam("删除的ID") @PathVariable("gradeId") String gradeId) {
        boolean removeById = gradeService.removeById(gradeId);
        if (removeById) {
            return ResultModel.success("删除成功!");
        }
        return ResultModel.error("删除失败!");
    }

    @ApiOperation("根据ID删除多个")
    @DeleteMapping("/deleteGrade")
    public ResultModel removeByIds(
            @ApiParam("删除的ID") @RequestBody List<Integer> ids) {
        boolean removeById = gradeService.removeByIds(ids);
        if (removeById) {
            return ResultModel.success("删除多个成功!");
        }
        return ResultModel.error("删除失败!");
    }

}
