package edu.jiangbaiyu.demo.spring.aop.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring AOP 基础演示启动类
 * @author Robin
 * @date 2026/03/09
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)  // 强制使用CGLIB代理，也可不配置让Spring自动选择
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}