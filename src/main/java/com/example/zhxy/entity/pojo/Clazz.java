package com.example.zhxy.entity.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("班级管理实体")
@Accessors(chain = true)
public class Clazz {
    private Integer id;
    private String grade;
    private int classSize;
    private String className;
    private String headTeacherName;
    private String headTeacherEmail;
    private String headTeacherPhone;
    private String classDescription;
}
