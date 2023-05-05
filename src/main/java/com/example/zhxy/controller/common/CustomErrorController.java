package com.example.zhxy.controller.common;


import com.example.zhxy.common.ResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "自定义错误页面模块")
@RestController
public class CustomErrorController implements ErrorController {
    @ApiOperation("自定错误页面显示")
    @RequestMapping("/error")
    public ResultModel error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return ResultModel.error(status.value(), status.getReasonPhrase(), "Sorry, the requested resource was not found");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public String getErrorPath() {
        return "/error";
    }
}
