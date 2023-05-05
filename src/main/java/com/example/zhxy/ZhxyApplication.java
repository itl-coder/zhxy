package com.example.zhxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.zhxy.mapper")
public class ZhxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhxyApplication.class, args);
    }

}
