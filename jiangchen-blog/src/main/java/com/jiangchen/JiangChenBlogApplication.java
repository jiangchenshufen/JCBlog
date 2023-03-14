package com.jiangchen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jiangchen.mapper")
public class JiangChenBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(JiangChenBlogApplication.class,args);
    }
}
