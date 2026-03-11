package edu.jiangbaiyu.demo.spring.thymeleaf.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

/**
 * 用户Web控制器，返回Thymeleaf视图
 * @author Robin
 */
@Controller
public class UserWebController {

    @GetMapping("/users")
    public String listUsers(Model model) {
        // 模拟用户数据
        List<User> users = Arrays.asList(
                new User(1, "张三", "zhangsan@example.com", 1),
                new User(2, "李四", "lisi@example.com", 1),
                new User(3, "王五", "wangwu@example.com", 0)
        );
        model.addAttribute("users", users);
        model.addAttribute("title", "用户列表 - Thymeleaf演示");
        return "users";
    }

    // 内部类模拟User实体
    public static class User {
        private Integer id;
        private String name;
        private String email;
        private Integer status;

        public User(Integer id, String name, String email, Integer status) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.status = status;
        }

        public Integer getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public Integer getStatus() { return status; }
    }
}