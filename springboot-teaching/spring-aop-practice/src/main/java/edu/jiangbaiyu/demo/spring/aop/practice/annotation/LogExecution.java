package edu.jiangbaiyu.demo.spring.aop.practice.annotation;

import java.lang.annotation.*;

/**
 * 自定义日志注解，标注在需要记录日志的方法上
 * @author Robin
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogExecution {
    /**
     * 操作类型，默认为空字符串
     */
    String value() default "";
}