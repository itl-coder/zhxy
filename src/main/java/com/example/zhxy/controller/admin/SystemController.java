package com.example.zhxy.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.common.ResultModel;
import com.example.zhxy.entity.pojo.User;
import com.example.zhxy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "管理员管理模块")
@RestController
@RequestMapping("/admin")
public class SystemController {
    @Autowired
    private UserService userService;

    @ApiOperation("管理员信息")
    @GetMapping("/getAdmins/{pageNo}/{pageSize}")
    public ResultModel getAdmins(
            @ApiParam(value = "当前页", required = true) @PathVariable("pageNo") Integer pageNo,
            @ApiParam(value = "每页大小", required = true) @PathVariable("pageSize") Integer pageSize,
            @ApiParam(value = "管理员名称") String adminUserName) {
        User user = new User();
        user.setUsername(adminUserName);
        user.setUserType("1");
        Page<User> page = new Page<>(pageNo, pageSize);
        // 通过服务层查询
        IPage<User> pageGrade = userService.getAdminByOpr(page, user);
        return ResultModel.success(pageGrade);
    }


    @ApiOperation("更新或添加")
    @PostMapping("/saveOrUpdateGrade")
    public ResultModel saveOrUpdateGrade(
            @ApiParam("班级信息") @RequestBody User user) {
        boolean saveOrUpdate = userService.saveOrUpdate(user);
        if (saveOrUpdate) {
            return ResultModel.success("操作成功!");
        }
        return ResultModel.error("操作失败!");
    }


    @ApiOperation("根据ID删除一个")
    @GetMapping("/remove/{gradeId}")
    public ResultModel saveOrUpdateGrade(
            @ApiParam("删除的ID") @PathVariable("gradeId") String gradeId) {
        boolean removeById = userService.removeById(gradeId);
        if (removeById) {
            return ResultModel.success("删除成功!");
        }
        return ResultModel.error("删除失败!");
    }

    @ApiOperation("根据ID删除多个")
    @DeleteMapping("/deleteGrade")
    public ResultModel removeByIds(
            @ApiParam("删除的ID") @RequestBody List<Integer> ids) {
        boolean removeById = userService.removeByIds(ids);
        if (removeById) {
            return ResultModel.success("删除多个成功!");
        }
        return ResultModel.error("删除失败!");
    }


}
