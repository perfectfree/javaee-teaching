package edu.jiangbaiyu.demo.spring.aop.basic.controller;

import edu.jiangbaiyu.demo.spring.aop.basic.service.ProductService;
import edu.jiangbaiyu.demo.spring.aop.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AOP 测试控制器
 * @author Robin
 */
@RestController
public class AopController {

    @Autowired
    private UserService userService;       // 有接口，JDK动态代理

    @Autowired
    private ProductService productService; // 无接口，CGLIB代理

    /**
     * 测试正常执行
     */
    @GetMapping("/aop/user/find")
    public String testUserFind(@RequestParam(defaultValue = "1") Integer id) {
        return userService.findUserById(id);
    }

    @GetMapping("/aop/user/save")
    public String testUserSave(@RequestParam(defaultValue = "Tom") String name) {
        userService.saveUser(name);
        return "save success";
    }

    /**
     * 测试异常
     */
    @GetMapping("/aop/user/error")
    public String testUserError(@RequestParam(defaultValue = "0") Integer id) {
        try {
            return userService.findUserById(id);
        } catch (IllegalArgumentException e) {
            return "捕获异常: " + e.getMessage();
        }
    }

    /**
     * 测试 ProductService（无接口，CGLIB）
     */
    @GetMapping("/aop/product/find")
    public String testProductFind(@RequestParam(defaultValue = "10") Integer id) {
        return productService.findProductById(id);
    }

    @GetMapping("/aop/product/delete")
    public String testProductDelete(@RequestParam(defaultValue = "5") Integer id) {
        productService.deleteProduct(id);
        return "delete success";
    }
}