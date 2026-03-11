package edu.jiangbaiyu.demo.spring.aop.basic.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志切面：演示五种通知类型
 * @author Robin
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * 定义切入点：匹配 service 包下所有类的所有方法
     */
    @Pointcut("execution(* edu.jiangbaiyu.demo.spring.aop.basic.service.*.*(..))")
    public void serviceLayer() {}

    /**
     * 前置通知：在方法执行前执行
     */
    @Before("serviceLayer()")
    public void beforeAdvice(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("【@Before】方法 " + methodName + " 即将执行，参数：" + Arrays.toString(args));
    }

    /**
     * 后置通知（最终通知）：方法执行后执行（无论是否抛出异常）
     */
    @After("serviceLayer()")
    public void afterAdvice(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("【@After】方法 " + methodName + " 执行完毕（最终通知）");
    }

    /**
     * 返回通知：方法正常返回后执行
     */
    @AfterReturning(value = "serviceLayer()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("【@AfterReturning】方法 " + methodName + " 正常返回，返回值：" + result);
    }

    /**
     * 异常通知：方法抛出异常后执行
     */
    @AfterThrowing(value = "serviceLayer()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("【@AfterThrowing】方法 " + methodName + " 抛出异常，异常信息：" + ex.getMessage());
    }

    /**
     * 环绕通知：完全控制方法执行
     */
    @Around("serviceLayer()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        long start = System.currentTimeMillis();

        System.out.println("【@Around】方法 " + methodName + " 环绕通知开始");
        Object result;
        try {
            result = pjp.proceed();  // 执行目标方法
        } catch (Throwable t) {
            System.out.println("【@Around】方法 " + methodName + " 抛出异常：" + t.getMessage());
            throw t;
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("【@Around】方法 " + methodName + " 执行耗时：" + elapsed + "ms");
        }
        System.out.println("【@Around】方法 " + methodName + " 环绕通知结束");
        return result;
    }
}