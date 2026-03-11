package edu.jiangbaiyu.demo.spring.aop.practice.annotation;

import java.lang.annotation.*;

/**
 * 自定义权限注解，标注在需要特定角色才能访问的方法上
 * @author Robin
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {
    /**
     * 允许访问的角色列表
     */
    String[] value() default {};
}