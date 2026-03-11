package edu.jiangbaiyu.demo.spring.aop.practice.aspect;

import edu.jiangbaiyu.demo.spring.aop.practice.annotation.LogExecution;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志记录切面，优先级次之（@Order(2)）
 * @author Robin
 */
@Aspect
@Component
@Order(2)  // 在权限检查之后执行
public class LoggingAspect {

    /**
     * 定义切入点：匹配所有带有 @LogExecution 注解的方法
     */
    @Pointcut("@annotation(edu.jiangbaiyu.demo.spring.aop.practice.annotation.LogExecution)")
    public void logExecutionPointcut() {}

    /**
     * 环绕通知：记录方法执行时间、入参和返回值
     */
    @Around("logExecutionPointcut()")
    public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = pjp.getSignature().toShortString();

        // 获取注解属性
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        LogExecution logExecution = signature.getMethod().getAnnotation(LogExecution.class);
        String operation = logExecution.value();

        System.out.println("【日志切面】操作类型：" + operation + "，方法：" + methodName);
        System.out.println("【日志切面】入参：" + Arrays.toString(pjp.getArgs()));

        Object result;
        try {
            result = pjp.proceed();
        } catch (Throwable t) {
            System.out.println("【日志切面】方法执行异常：" + t.getMessage());
            throw t;
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("【日志切面】方法执行耗时：" + elapsed + "ms");
        }

        System.out.println("【日志切面】返回值：" + result);
        return result;
    }
}