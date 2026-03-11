package edu.jiangbaiyu.demo.springbootrestful.controller;

import edu.jiangbaiyu.demo.springbootrestful.dto.Result;
import edu.jiangbaiyu.demo.springbootrestful.entity.User;
import edu.jiangbaiyu.demo.springbootrestful.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Tag(name = "用户管理", description = "用户增删改查接口")
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Operation(summary = "创建用户", description = "传入User JSON，返回创建的用户")
    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        long id = idGenerator.getAndIncrement();
        user.setId(id);
        userMap.put(id, user);
        return Result.success(user);
    }

    @Operation(summary = "根据ID查询用户")
    @GetMapping("/{id}")
    public Result<User> getUserById(@Parameter(description = "用户ID") @PathVariable Long id) {
        User user = userMap.get(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return Result.success(user);
    }

    @Operation(summary = "查询所有用户")
    @GetMapping
    public Result<Map<Long, User>> getAllUsers() {
        return Result.success(userMap);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!userMap.containsKey(id)) {
            throw new BusinessException(404, "用户不存在");
        }
        user.setId(id);
        userMap.put(id, user);
        return Result.success(user);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        userMap.remove(id);
        return Result.success("删除成功");
    }
}