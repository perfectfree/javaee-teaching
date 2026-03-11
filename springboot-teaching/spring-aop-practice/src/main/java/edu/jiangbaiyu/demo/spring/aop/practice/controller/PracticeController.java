package edu.jiangbaiyu.demo.spring.aop.practice.controller;

import edu.jiangbaiyu.demo.spring.aop.practice.service.OrderService;
import edu.jiangbaiyu.demo.spring.aop.practice.service.UserService;
import edu.jiangbaiyu.demo.spring.aop.practice.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 实践控制器，用于测试自定义注解和AOP
 * @author Robin
 */
@RestController
@RequestMapping("/practice")
public class PracticeController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * 设置当前用户角色（通过请求参数模拟登录）
     */
    @GetMapping("/setRole")
    public String setRole(@RequestParam(value = "role") String role) {
        UserContext.setCurrentRole(role);
        return "当前用户角色已设置为: " + role;
    }

    /**
     * 清除角色（登出）
     */
    @GetMapping("/clearRole")
    public String clearRole() {
        UserContext.clear();
        return "用户角色已清除，默认为 GUEST";
    }

    /**
     * 测试无权限要求的方法
     */
    @GetMapping("/user/find")
    public String findUser(@RequestParam(value = "id") Integer id) {
        return userService.findUserById(id);
    }

    /**
     * 测试需要 ADMIN 权限的方法
     */
    @PostMapping("/user/create")
    public String createUser(@RequestParam(value = "name") String name) {
        return userService.createUser(name);
    }

    /**
     * 测试需要 ADMIN/MANAGER 权限的方法
     */
    @DeleteMapping("/user/delete")
    public String deleteUser(@RequestParam(value = "id") Integer id) {
        return userService.deleteUser(id);
    }

    /**
     * 测试订单查询（无权限要求）
     */
    @GetMapping("/order/find")
    public String findOrder(@RequestParam(value = "id") Integer id) {
        return orderService.findOrderById(id);
    }

    /**
     * 测试创建订单（需要 ADMIN）
     */
    @PostMapping("/order/create")
    public String createOrder(@RequestParam(value = "orderNo") String orderNo) {
        return orderService.createOrder(orderNo);
    }

    /**
     * 测试内部调用失效问题（直接调用本类方法，AOP不生效）
     */
    @GetMapping("/user/internalCall")
    public String internalCall(@RequestParam(value = "name") String name) {
        // 这里直接调用 UserService 的 internalCallDemo，其内部调用 createUser 不会触发AOP
        return userService.internalCallDemo(name);
    }

    /**
     * 修复内部调用：通过代理对象调用（这里演示在控制器中直接调用createUser，已经通过代理）
     * 实际上，如果需要在同一个类中调用带注解的方法，可以注入自身代理（@Lazy + 自我注入）
     * 为简化，此处不实现，只做提示。
     */
    @GetMapping("/user/internalCallFixed")
    public String internalCallFixed(@RequestParam(value = "name") String name) {
        // 直接调用 createUser，会触发AOP，因为是通过代理对象调用的
        return userService.createUser(name) + " (通过控制器直接调用代理)";
    }
}