package com.example.zhxy.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("返回前后端统一结果")
public class ResultModel<T> implements Serializable {
    //状态码
    @ApiModelProperty(value = "状态码")
    private int code; // 1000表示成功 401表示认证失败

    //消息
    @ApiModelProperty(value = "本次请求消息(成功或错误提示)")
    private String message;
    //数据
    @ApiModelProperty(value = "本次请求返回的数据")
    private T data;

    private static ResultModel resultModel = new ResultModel();

    public static ResultModel success(String message) {
        resultModel.setCode(1000);
        resultModel.setMessage(message);
        resultModel.setData(null);
        return resultModel;
    }

    public static ResultModel success(Object data) {
        resultModel.setCode(200);
        resultModel.setMessage("success");
        resultModel.setData(data);
        return resultModel;
    }

    public static ResultModel success(String message, Object data) {
        resultModel.setCode(200);
        resultModel.setMessage(message);
        resultModel.setData(data);
        return resultModel;
    }

    public static ResultModel success(Integer code, String message) {
        resultModel.setCode(200);
        resultModel.setMessage(message);
        return resultModel;
    }

    public static ResultModel success(Integer code, String message, Object data) {
        resultModel.setCode(code);
        resultModel.setMessage(message);
        resultModel.setData(data);
        return resultModel;
    }

    public static ResultModel error() {
        resultModel.setCode(500);
        resultModel.setMessage("error");
        resultModel.setData(null);
        return resultModel;
    }

    public static ResultModel error(String message) {
        resultModel.setCode(500);
        resultModel.setMessage(message);
        resultModel.setData(null);
        return resultModel;
    }

    public static ResultModel error(Integer code, String message, Object data) {
        resultModel.setCode(code);
        resultModel.setMessage(message);
        resultModel.setData(data);
        return resultModel;
    }

    public static ResultModel error(int code, String message) {
        resultModel.setCode(code);
        resultModel.setMessage(message);
        resultModel.setData(null);
        return resultModel;
    }
}
