package edu.jiangbaiyu.demo.spring.ioc.controller;

import edu.jiangbaiyu.demo.spring.ioc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器，演示依赖注入的三种方式
 * @author Robin
 */
@RestController
public class UserController {

    // ========== 1. 属性注入（不推荐） ==========
    @Autowired
    @Qualifier("mockUserService")  // 指定使用 mockUserService 实现
    private UserService userServiceByField;

    @GetMapping("/user/field")
    public String getUserByField() {
        return "Field Injection: " + userServiceByField.getUserInfo();
    }

    // ========== 2. Setter注入 ==========
    private UserService userServiceBySetter;

    @Autowired
    @Qualifier("userServiceImpl")  // 指定使用默认的 userServiceImpl
    public void setUserServiceBySetter(UserService userService) {
        this.userServiceBySetter = userService;
    }

    @GetMapping("/user/setter")
    public String getUserBySetter() {
        return "Setter Injection: " + userServiceBySetter.getUserInfo();
    }

    // ========== 3. 构造器注入（推荐） ==========
    private final UserService userServiceByConstructor;

    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserService userService) {
        this.userServiceByConstructor = userService;
    }

    @GetMapping("/user/constructor")
    public String getUserByConstructor() {
        return "Constructor Injection: " + userServiceByConstructor.getUserInfo();
    }

    // ========== 额外演示：不指定Qualifier会报错（多个实现）==========
    // 如果想演示歧义，可以取消下面代码的注释，但需要注释掉一个实现类上的@Service
    /*
    @Autowired
    private UserService ambiguousService;  // 启动会报错，因为有两个匹配的bean

    @GetMapping("/user/ambiguous")
    public String getAmbiguous() {
        return ambiguousService.getUserInfo();
    }
    */
}