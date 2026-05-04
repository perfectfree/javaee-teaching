package edu.jiangbaiyu.demo.springboot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 配置读取演示应用
 * @author Robin
 * @date 2026/03/09
 */
@SpringBootApplication
//@EnableConfigurationProperties  // 启用 @ConfigurationProperties 支持
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}