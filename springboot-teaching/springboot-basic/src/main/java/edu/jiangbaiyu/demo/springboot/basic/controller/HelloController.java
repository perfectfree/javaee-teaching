package edu.jiangbaiyu.demo.springboot.basic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第一个 REST Controller
 * @author Robin
 * @date 2026/03/09
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot";
    }

    @GetMapping("/info")
    public String info() {
        // 可替换为自己的姓名学号
        return "Name: Robin, ID: 2026001";
    }
}