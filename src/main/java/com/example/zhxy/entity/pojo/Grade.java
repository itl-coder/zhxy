package com.example.zhxy.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("年级管理实体")
@Accessors(chain = true)
public class Grade implements Serializable {
    @ApiModelProperty(value = "班级ID")
    @TableId
    private int gradeId;
    @ApiModelProperty(value = "年级名称")
    private String gradeName;
    @ApiModelProperty(value = "年级主任")
    private String gradeDirector;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "年级介绍")
    private String gradeDescription;
}
