package com.example.zhxy.controller.admin;

import com.example.zhxy.common.ResultModel;
import com.example.zhxy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Api(tags = "文件上传模块")
@RestController
@RequestMapping("/common")
public class UploadCommonController {
    @Value("${zhxy.header-img}")
    private String uploadPath;
    @Autowired
    private UserService userService;

    @ApiOperation("文件上传")
    @PostMapping("/upload/headerImgUpload")
    public ResultModel headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile) {
        // multipartFile 是一个临时文件,需要转存到指定位置,否则本次请求完成后临时文件会自动删除
        log.info("multipartFile:{}", multipartFile.toString());
        // 生成一个新的文件名称
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        // 获取原始的文件名称
        String originalFilename = multipartFile.getOriginalFilename();
        log.info("originalFilename: {}", originalFilename);
        // 获取 . 以后的信息
        int pointLastIndex = originalFilename.lastIndexOf(".");
        log.info("pointLastIndex: {}", pointLastIndex);
        String newFileName = uuid + originalFilename.substring(pointLastIndex);
        log.info("newFileName: {}", newFileName);
        String finalPath = uploadPath + newFileName;
        // 保存文件
        try {
            // 相应文件路径
            // 判断文件夹路径是否存在
            // 定义文件夹路径
            // 创建 File 对象
            File folder = new File(uploadPath);

            // 判断文件夹是否存在
            if (!folder.exists()) {
                // 如果文件夹不存在则创建
                boolean result = folder.mkdirs();
                if (result) {
                    multipartFile.transferTo(new File(finalPath));
                } else {
                    log.error("文件夹创建失败...............");
                }
            } else {
                multipartFile.transferTo(new File(uploadPath + newFileName));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 存储上传文件路径

        return ResultModel.success("文件上传成功", newFileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            // 输入流: 通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(uploadPath + name));
            // 设置响应的格式
            response.setContentType("image/jpeg");
            // 输出流: 通过输出流将文件写回浏览器,在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                // 通过输出流每次写 bytes
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            // 关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
