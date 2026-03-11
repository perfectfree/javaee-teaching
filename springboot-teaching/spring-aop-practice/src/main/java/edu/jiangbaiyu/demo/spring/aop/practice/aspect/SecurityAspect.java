package edu.jiangbaiyu.demo.spring.aop.practice.aspect;

import edu.jiangbaiyu.demo.spring.aop.practice.annotation.RequireRole;
import edu.jiangbaiyu.demo.spring.aop.practice.util.UserContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 权限检查切面，优先级最高（@Order(1)）
 * @author Robin
 */
@Aspect
@Component
@Order(1)  // 数值越小优先级越高，权限检查先执行
public class SecurityAspect {

    @Around("@annotation(requireRole)")
    public Object checkRole(ProceedingJoinPoint pjp, RequireRole requireRole) throws Throwable {
        // 获取当前用户角色（模拟，实际应从Session或SecurityContext获取）
        String currentRole = UserContext.getCurrentRole();
        String[] allowedRoles = requireRole.value();

        System.out.println("【权限切面】当前用户角色：" + currentRole + "，允许的角色：" + Arrays.toString(allowedRoles));

        // 如果注解未指定角色，默认允许
        if (allowedRoles.length == 0) {
            return pjp.proceed();
        }

        // 检查当前角色是否在允许的角色列表中
        boolean hasPermission = Arrays.asList(allowedRoles).contains(currentRole);
        if (!hasPermission) {
            throw new SecurityException("权限不足，需要角色：" + Arrays.toString(allowedRoles));
        }

        System.out.println("【权限切面】权限验证通过");
        return pjp.proceed();
    }
}