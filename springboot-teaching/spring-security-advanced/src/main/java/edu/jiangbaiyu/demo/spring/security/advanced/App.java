package edu.jiangbaiyu.demo.spring.security.advanced;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Spring Security 高级演示启动类
 * @author Robin
 * @date 2026/03/09
 */
@SpringBootApplication
@MapperScan("edu.jiangbaiyu.demo.spring.security.advanced.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 启用方法级安全
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}