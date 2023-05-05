package com.example.zhxy.entity.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色实体")
public class User {
    @ApiModelProperty(value = "用户ID")
    private Integer id;
    @ApiModelProperty(value = "用户名称")
    private String username;
    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;
    @ApiModelProperty(value = "账户是否不可用")
    private Integer noEnabled;
    @ApiModelProperty(value = "账户是否未过期")
    private Integer noAccountNonExpired;
    @ApiModelProperty(value = "账户是否锁定")
    private Integer noAccountNonLocked;
    @ApiModelProperty(value = "用户凭证")
    private Integer noCredentialsNonExpired;
    private String sex;
    private String email;
    private String phone;
    private String address;
    private String photo;
    private String userType;
    // 用户的角色集合
    @ApiModelProperty(value = "用户角色集合")
    @TableField(exist = false)
    private List<Role> roles = new ArrayList<>();
}