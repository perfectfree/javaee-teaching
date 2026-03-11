package edu.jiangbaiyu.demo.spring.mvc.rest.controller;

import edu.jiangbaiyu.demo.spring.mvc.rest.entity.User;
import edu.jiangbaiyu.demo.spring.mvc.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户 RESTful 控制器
 * @author Robin
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有用户
     * GET /users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * 根据ID查询单个用户
     * GET /users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 创建新用户
     * POST /users
     * 请求体JSON自动绑定到User对象
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userService.save(user);
        // 返回201 Created，并在Location头中提供新资源URI
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * 更新用户
     * PUT /users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        User updated = userService.update(id, user);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    /**
     * 删除用户
     * DELETE /users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        User existing = userService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

    /**
     * 演示 @RequestParam：按状态查询
     * GET /users/filter?status=1
     */
    @GetMapping("/filter")
    public List<User> getUsersByStatus(@RequestParam(required = false) Integer status) {
        if (status == null) {
            return userService.findAll();
        }
        // 简单过滤：实际应用中可扩展
        return userService.findAll().stream()
                .filter(u -> status.equals(u.getStatus()))
                .toList();
    }
}