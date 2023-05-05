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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "教职工管理模块")
@RestController
@RequestMapping("/teacher/getTeacher")
public class TeacherController {

    @Autowired
    private UserService userService;

    @ApiOperation("教职工信息")
    @GetMapping("/{pageNo}/{pageSize}")
    public ResultModel getAdmins(
            @ApiParam(value = "当前页", required = true) @PathVariable("pageNo") Integer pageNo,
            @ApiParam(value = "每页大小", required = true) @PathVariable("pageSize") Integer pageSize,
            @ApiParam(value = "管理员名称") String teacherName) {
        User user = new User();
        user.setUsername(teacherName);
        user.setUserType("2");
        Page<User> page = new Page<>(pageNo, pageSize);
        // 通过服务层查询
        IPage<User> pageGrade = userService.getTeacherByOpr(page, user);
        return ResultModel.success(pageGrade);
    }
}
