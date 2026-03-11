package edu.jiangbaiyu.demo.spring.bean.scope.bean;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * 单例作用域 Bean（默认）
 * 演示 singleton 作用域下，对象地址不变，计数值共享
 * @author Robin
 */
@Component  // 默认作用域为 singleton
public class SingletonBean {

    private int count = 0;

    public int incrementAndGet() {
        return ++count;
    }

    // 用于显示对象地址
    public String getInfo() {
        return "SingletonBean@" + Integer.toHexString(hashCode()) + ", count=" + count;
    }
}