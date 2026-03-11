package edu.jiangbaiyu.demo.spring.ioc.config;

import edu.jiangbaiyu.demo.spring.ioc.service.UserService;
import edu.jiangbaiyu.demo.spring.ioc.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Java Config 配置类，演示使用 @Bean 手动注册 Bean
 * @author Robin
 */
@Configuration
public class AppConfig {

    @Bean(name = "userServiceFromConfig")
    public UserService userService() {
        // 可以在这里做额外的初始化
        return new UserServiceImpl();
    }
}