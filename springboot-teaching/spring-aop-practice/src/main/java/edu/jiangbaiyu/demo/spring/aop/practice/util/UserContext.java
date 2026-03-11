package edu.jiangbaiyu.demo.spring.aop.practice.util;

/**
 * 模拟用户上下文，存储当前线程的用户角色
 * @author Robin
 */
public class UserContext {

    private static final ThreadLocal<String> currentRole = new ThreadLocal<>();

    public static void setCurrentRole(String role) {
        currentRole.set(role);
    }

    public static String getCurrentRole() {
        String role = currentRole.get();
        return role == null ? "GUEST" : role;  // 默认游客
    }

    public static void clear() {
        currentRole.remove();
    }
}