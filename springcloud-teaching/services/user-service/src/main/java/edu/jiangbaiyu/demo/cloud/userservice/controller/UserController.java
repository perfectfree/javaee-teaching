package edu.jiangbaiyu.demo.cloud.userservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import edu.jiangbaiyu.demo.cloud.common.result.R;
import edu.jiangbaiyu.demo.cloud.userservice.entity.User;
import edu.jiangbaiyu.demo.cloud.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器，提供RESTful API
 * @author Robin
 * @date 2026/03/09
 */
@RefreshScope
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${app.greeting:默认问候}")
    private String greeting;

    @GetMapping("/greeting")
    public R<String> greeting() {
        return R.success(greeting);
    }

    /**
     * 新增用户
     * POST /users
     */
    @PostMapping
    public R<User> add(@Valid @RequestBody User user) {
        return R.success(userService.addUser(user));
    }

    /**
     * 根据ID查询用户
     * GET /users/{id}
     */
    @GetMapping("/{id}")
    public R<User> getById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return R.error(404, "用户不存在");
        }
        return R.success(user);
    }

    /**
     * 更新用户
     * PUT /users
     */
    @PutMapping
    public R<Boolean> update(@Valid @RequestBody User user) {
        return R.success(userService.updateUser(user));
    }

    /**
     * 删除用户
     * DELETE /users/{id}
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable("id") Long id) {
        return R.success(userService.deleteUser(id));
    }

    /**
     * 演示 @SentinelResource 限流
     */
    @GetMapping("/sentinel/{id}")
    @SentinelResource(value = "getUserByIdWithSentinel", blockHandler = "blockHandlerForGetUser")
    public R<User> getUserWithSentinel(@PathVariable("id") Long id) {
        return R.success(userService.getById(id));
    }

    // blockHandler 方法，参数和返回值需与原方法一致，且必须包含 BlockException
    public R<User> blockHandlerForGetUser(Long id, BlockException ex) {
        return R.error(429, "请求被限流: " + ex.getClass().getSimpleName());
    }

    // 模拟慢调用，用于熔断演示
    @GetMapping("/slow/{id}")
    @SentinelResource(value = "slowGetUser", fallback = "fallbackForSlow")
    public R<User> slowGetUser(@PathVariable("id") Long id) throws InterruptedException {
        Thread.sleep(30000); // 模拟慢调用
        return R.success(userService.getById(id));
    }

    public R<User> fallbackForSlow(Long id, Throwable t) {
        return R.error(500, "服务降级: " + t.getMessage());
    }
}