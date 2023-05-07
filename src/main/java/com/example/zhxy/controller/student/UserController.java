package com.example.zhxy.controller.student;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
@Api(tags = "用户管理模块")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("用户信息")
    @GetMapping("/getUsers/{pageNo}/{pageSize}")
    public ResultModel getUsers(@ApiParam(value = "当前页", required = true) @PathVariable("pageNo") Integer pageNo, @ApiParam(value = "每页大小", required = true) @PathVariable("pageSize") Integer pageSize, @ApiParam(value = "管理员名称") String username) {
        User user = new User();
        if (!StringUtils.isEmpty(username)) {
            user.setUsername(username);
        }
        user.setUserType("3");
        Page<User> page = new Page<>(pageNo, pageSize);
        // 通过服务层查询
        IPage<User> pageUser = userService.getUserByOpr(page, user);
        return ResultModel.success(pageUser);
    }

    /**
     * 实现更新或添加班级信息
     *
     * @return
     */
    @ApiOperation("更新或添加")
    @PostMapping("/save")
    public ResultModel saveOrUpdateUser(@ApiParam("用户信息") @RequestBody User user) {
        log.info("user: {}", user.toString());
        if (user.getId() != null) {
            LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
            userQueryWrapper.eq(user.getId() != null, User::getId, user.getId());
            boolean res = userService.saveOrUpdate(user, userQueryWrapper);
            if (res) {
                return ResultModel.success("更新用户信息成功!");
            }
            return ResultModel.error("更新用户信息失败!");
        }
        boolean save = userService.save(user);
        if (save) {
            return ResultModel.success("添加用户成功!");
        }
        return ResultModel.error("添加用户失败!");
    }

    @ApiOperation("根据ID删除一个")
    @GetMapping("/remove/{id}")
    public ResultModel saveOrUpdateGrade(@ApiParam("删除的ID") @PathVariable("id") String id) {
        boolean removeById = userService.removeById(id);
        if (removeById) {
            return ResultModel.success("删除成功!");
        }
        return ResultModel.error("删除失败!");
    }

    @ApiOperation("根据ID删除多个")
    @DeleteMapping("/deleteUser")
    public ResultModel removeByIds(@ApiParam("删除的ID") @RequestBody List<Integer> ids) {
        boolean removeById = userService.removeByIds(ids);
        if (removeById) {
            return ResultModel.success("删除多个成功!");
        }
        return ResultModel.error("删除失败!");
    }

}