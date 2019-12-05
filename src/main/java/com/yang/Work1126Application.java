package com.yang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.yang.dao")
public class Work1126Application {

    public static void main(String[] args) {
        SpringApplication.run(Work1126Application.class, args);
    }

}
