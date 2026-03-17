package edu.jiangbaiyu.demo.spring.aop.practice.config;

import edu.jiangbaiyu.demo.spring.aop.practice.interceptor.RoleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RoleInterceptor())
                .addPathPatterns("/practice/**");  // 拦截所有 practice 相关请求
    }
}