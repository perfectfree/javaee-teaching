package edu.jiangbaiyu.demo.cloud.departmentservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("edu.jiangbaiyu.demo.cloud.common;edu.jiangbaiyu.demo.cloud.departmentservice")
@MapperScan("edu.jiangbaiyu.demo.cloud.departmentservice.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}