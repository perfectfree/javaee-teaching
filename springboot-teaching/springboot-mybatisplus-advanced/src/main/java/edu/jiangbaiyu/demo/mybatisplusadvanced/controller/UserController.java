package edu.jiangbaiyu.demo.mybatisplusadvanced.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.jiangbaiyu.demo.mybatisplusadvanced.entity.User;
import edu.jiangbaiyu.demo.mybatisplusadvanced.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * 提供 REST API 演示 MyBatis-Plus 高级特性
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 添加用户
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // 条件查询
    @GetMapping("/search")
    public List<User> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String email) {
        return userService.searchUsers(name, minAge, maxAge, email);
    }

    // 分页查询
    @GetMapping("/page")
    public Page<User> page(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String name) {
        return userService.getUsersPage(current, size, name);
    }

    // 更新用户
    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    // 删除用户（逻辑删除）
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "逻辑删除成功";
    }

    // 查询所有（包括已逻辑删除的，用于对比）
    @GetMapping("/all-include-deleted")
    public List<User> allIncludeDeleted() {
        return userService.listAllIncludeDeleted();
    }

    // 根据ID查询（自动过滤逻辑删除）
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUsersPage(1, 1, null).getRecords().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElse(null);
    }
}