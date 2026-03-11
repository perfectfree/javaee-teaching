package edu.jiangbaiyu.demo.spring.security.basic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * @author Robin
 */
@RestController
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, this is a public endpoint.";
    }

    @GetMapping("/user/info")
    public String userInfo() {
        return "User info (requires authentication)";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Admin dashboard (requires ADMIN role)";
    }
}