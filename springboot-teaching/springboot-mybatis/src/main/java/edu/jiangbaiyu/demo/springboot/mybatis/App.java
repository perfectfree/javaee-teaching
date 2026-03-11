package edu.jiangbaiyu.demo.springboot.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot 整合 MyBatis 启动类
 * @author Robin
 * @date 2026/03/09
 */
@SpringBootApplication
@MapperScan("edu.jiangbaiyu.demo.springboot.mybatis.mapper")  // 扫描Mapper接口
@EnableTransactionManagement  // 开启声明式事务管理（Spring Boot默认已开启，显式标注更清晰）
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}