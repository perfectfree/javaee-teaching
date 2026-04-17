package edu.jiangbaiyu.demo.cloud.authservice.controller;

import edu.jiangbaiyu.demo.cloud.authservice.service.AuthService;
import edu.jiangbaiyu.demo.cloud.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public R<Map<String, String>> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        String token = authService.login(username, password);
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return R.success(data);
    }
}