package edu.jiangbaiyu.demo.springbootcore.controller;

import edu.jiangbaiyu.demo.springbootcore.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用户REST控制器（内存存储）
 * 演示@RestController, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
 * 以及路径变量、请求参数、请求体接收
 */
@RestController
@RequestMapping("/users")
public class UserController {
    // 模拟数据库
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostMapping
    public User createUser(@RequestBody User user) {
        long id = idGenerator.getAndIncrement();
        user.setId(id);
        userMap.put(id, user);
        return user;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userMap.get(id);
    }

    @GetMapping
    public Map<Long, User> getAllUsers() {
        return userMap;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        userMap.put(id, user);
        return user;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userMap.remove(id);
        return "删除成功";
    }

    // 演示请求参数接收
    @GetMapping("/search")
    public User searchUser(@RequestParam(required = false) String name,
                           @RequestParam(defaultValue = "0") Integer age) {
        // 简单模拟：返回第一个匹配名称的用户（实际开发应遍历）
        return userMap.values().stream()
                .filter(u -> name != null && name.equals(u.getName()))
                .findFirst().orElse(null);
    }
}