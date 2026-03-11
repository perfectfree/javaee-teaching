package edu.jiangbaiyu.demo.spring.bean.scope.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 生命周期演示 Bean
 * 演示 @PostConstruct, @PreDestroy, InitializingBean, DisposableBean 回调
 * @author Robin
 */
@Component
public class LifecycleBean implements InitializingBean, DisposableBean {

    public LifecycleBean() {
        System.out.println("【LifecycleBean】构造函数执行");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("【LifecycleBean】@PostConstruct 初始化回调");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【LifecycleBean】InitializingBean.afterPropertiesSet() 回调");
    }

    // 自定义初始化方法（可通过 @Bean(initMethod) 指定，这里为了完整演示）
    public void customInit() {
        System.out.println("【LifecycleBean】自定义 init 方法 (initMethod)");
    }

    public void doWork() {
        System.out.println("【LifecycleBean】执行业务方法");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("【LifecycleBean】@PreDestroy 销毁前回调");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("【LifecycleBean】DisposableBean.destroy() 回调");
    }

    // 自定义销毁方法
    public void customDestroy() {
        System.out.println("【LifecycleBean】自定义 destroy 方法 (destroyMethod)");
    }
}