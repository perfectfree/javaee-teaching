package edu.jiangbaiyu.demo.springboot.mybatis.controller;

import edu.jiangbaiyu.demo.springboot.mybatis.entity.User;
import edu.jiangbaiyu.demo.springboot.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户RESTful控制器
 * @author Robin
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        User updated = userService.update(user);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 搜索接口（动态条件）
     */
    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer deptId) {
        return userService.search(name, email, status, deptId);
    }

    /**
     * 关联查询（用户+部门）
     */
    @GetMapping("/withDept")
    public List<User> getUsersWithDept() {
        return userService.findUsersWithDept();
    }

    /**
     * 测试事务回滚
     */
    @PostMapping("/transfer")
    public String transferDept(@RequestParam Integer userId,
                               @RequestParam Integer newDeptId,
                               @RequestParam(defaultValue = "false") boolean simulateError) {
        if (simulateError) {
            newDeptId = 999;  // 触发异常
        }
        userService.transferUserDept(userId, newDeptId);
        return "部门转移成功";
    }
}