package com.example.zhxy.controller.common;

import com.example.zhxy.common.ResultModel;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@Api(tags = "验证码模块")
@RestController
@RequestMapping("/common")
public class CaptchaController {
    private final Producer producer;
    private final RedisTemplate redisTemplate;

    @Autowired
    public CaptchaController(Producer producer, RedisTemplate redisTemplate) {
        this.producer = producer;
        this.redisTemplate = redisTemplate;
    }

    @ApiOperation("获取验证码")
    @GetMapping("/captcha")
    public ResultModel getVerifyCode() throws IOException {
        // 1. 生成验证码
        String text = producer.createText();
        log.info("code text: {}", text);
        // 2. TODO 放入 session/redis
        // redisTemplate.opsForValue().set("kaptcha", text, 5, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("kaptcha", text);
        // 3. 生成图片
        BufferedImage image = producer.createImage(text);
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(image, "jpg", fos);
        String base64Img = Base64.encodeBase64String(fos.toByteArray());
        return ResultModel.success(HttpStatus.OK.value(), "验证码获取成功!", base64Img);
    }
}
