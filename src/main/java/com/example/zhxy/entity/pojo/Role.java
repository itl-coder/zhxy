package com.example.zhxy.entity.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色实体")
public class Role implements Serializable {
    @ApiModelProperty(value = "角色ID")
    private Integer id;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "角色中文名称")
    private String nameZh;
}