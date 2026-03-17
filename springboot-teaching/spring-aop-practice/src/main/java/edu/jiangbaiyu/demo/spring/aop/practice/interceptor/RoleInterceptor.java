package edu.jiangbaiyu.demo.spring.aop.practice.interceptor;

import edu.jiangbaiyu.demo.spring.aop.practice.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 从 Session 获取角色
        String role = (String) request.getSession().getAttribute("currentRole");
        if (role != null) {
            UserContext.setCurrentRole(role);
        } else {
            UserContext.setCurrentRole("GUEST");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        // 请求结束后清理
        UserContext.clear();
    }
}