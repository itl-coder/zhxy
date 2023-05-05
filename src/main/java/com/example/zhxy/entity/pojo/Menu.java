package com.example.zhxy.entity.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("资源菜单实体")
public class Menu implements Serializable {
    @ApiModelProperty(value = "菜单 ID")
    private Integer id;
    @ApiModelProperty(value = "菜单映射路径")
    private String pattern;
    // 这个菜单所需要的角色信息
    @ApiModelProperty(value = "菜单所需要的角色信息")
    private List<Role> roles;
}
