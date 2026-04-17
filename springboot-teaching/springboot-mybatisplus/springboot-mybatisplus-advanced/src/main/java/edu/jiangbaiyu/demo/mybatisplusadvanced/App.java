package edu.jiangbaiyu.demo.mybatisplusadvanced;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MyBatis-Plus 高级特性演示应用
 * @author Robin
 * @date 2026/03/09
 */
@SpringBootApplication
@MapperScan("edu.jiangbaiyu.demo.mybatisplusadvanced.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}