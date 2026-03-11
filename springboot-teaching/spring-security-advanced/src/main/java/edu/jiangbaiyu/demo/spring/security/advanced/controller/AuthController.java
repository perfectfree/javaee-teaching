package edu.jiangbaiyu.demo.spring.security.advanced.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证相关控制器
 * @author Robin
 */
@RestController
public class AuthController {

    @GetMapping("/public/test")
    public String test() {
        return "Public test";
    }
}