package com.example.zhxy.controller.common;

import com.example.zhxy.common.ResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "权限测试模块")
@RestController
public class HelloController {
    @ApiOperation("管理员权限访问")
    @GetMapping("/admin/hello")
    public ResultModel adminHello() {
        return ResultModel.success(HttpStatus.OK.value(), "数据获取成功", "Hello SpringSecurity Admin!");
    }

    @ApiOperation("教职工权限访问")
    @GetMapping("/teacher/hello")
    public ResultModel teacherHello() {
        return ResultModel.success(HttpStatus.OK.value(), "数据获取成功", "Hello SpringSecurity ADMIN and Teacher!");
    }

    @ApiOperation("普通用户权限访问")
    @GetMapping("/user/hello")
    public ResultModel studentHello() {
        return ResultModel.success(HttpStatus.OK.value(), "数据获取成功", "Hello SpringSecurity Student!");
    }

    @ApiOperation("全体人员访问")
    @GetMapping("/hello")
    public ResultModel hello() {
        return ResultModel.success(HttpStatus.OK.value(), "数据获取成功", "Hello SpringSecurity All!");
    }
}
