package edu.jiangbaiyu.demo.spring.bean.scope.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 原型作用域 Bean
 * 每次获取都创建新实例，计数值独立
 * @author Robin
 */
@Component
@Scope("prototype")  // 指定为原型作用域
public class PrototypeBean {

    private int count = 0;

    public int incrementAndGet() {
        return ++count;
    }

    public String getInfo() {
        return "PrototypeBean@" + Integer.toHexString(hashCode()) + ", count=" + count;
    }
}