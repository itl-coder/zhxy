package com.example.zhxy.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {

    public static void out(HttpServletResponse response, ResultModel resultModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 封装 response 的状态码和内容格式
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 内容：resultModel json
        try {
            // 使用jackson，把json格式的resultModel写入到response的输出流中
            objectMapper.writeValue(response.getOutputStream(), resultModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}