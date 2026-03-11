package edu.jiangbaiyu.demo.transactionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 事务管理演示应用启动类
 * @author Robin
 * @date 2026/03/09
 */
@SpringBootApplication
@MapperScan("edu.jiangbaiyu.demo.transactionservice.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}