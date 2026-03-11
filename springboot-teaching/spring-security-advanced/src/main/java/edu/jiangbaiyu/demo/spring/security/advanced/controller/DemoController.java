package edu.jiangbaiyu.demo.spring.security.advanced.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器，演示方法级安全
 * @author Robin
 */
@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Public endpoint, no authentication required.";
    }

    @GetMapping("/user/info")
    @PreAuthorize("hasRole('USER')")
    public String userInfo() {
        return "User info, requires USER role.";
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Admin dashboard, requires ADMIN role.";
    }
}