package edu.jiangbaiyu.demo.transactionservice.controller;

import edu.jiangbaiyu.demo.transactionservice.dto.Result;
import edu.jiangbaiyu.demo.transactionservice.entity.User;
import edu.jiangbaiyu.demo.transactionservice.exception.BusinessException;
import edu.jiangbaiyu.demo.transactionservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器，提供REST接口测试事务管理
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建用户（不带事务）
     */
    @PostMapping("/no-tx")
    public Result<User> createWithoutTx(@RequestBody User user) {
        return Result.success(userService.createUserWithoutTransaction(user));
    }

    /**
     * 创建用户并记录日志（正常情况）
     */
    @PostMapping("/with-log")
    public Result<User> createWithLog(@RequestBody User user) {
        return Result.success(userService.createUserWithLog(user));
    }

    /**
     * 创建用户并记录日志（日志抛出异常，整体回滚）
     */
    @PostMapping("/with-log-error")
    public Result<User> createWithLogError(@RequestBody User user) {
        try {
            return Result.success(userService.createUserWithLogAndError(user));
        } catch (Exception e) {
            return Result.error(500, "事务已回滚: " + e.getMessage());
        }
    }

    /**
     * 创建用户，日志独立事务（REQUIRES_NEW），日志失败不影响用户创建
     */
    @PostMapping("/with-independent-log")
    public Result<User> createWithIndependentLog(@RequestBody User user) {
        return Result.success(userService.createUserWithIndependentLog(user));
    }

    /**
     * 查询用户（只读事务）
     */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return Result.success(user);
    }

    /**
     * 更新用户并演示noRollbackFor
     */
    @PutMapping("/no-rollback")
    public Result<String> updateWithNoRollback(@RequestBody User user) {
        try {
            userService.updateUserWithNoRollback(user);
        } catch (IllegalArgumentException e) {
            // 异常被noRollbackFor忽略，事务已提交
            return Result.success("更新成功，尽管抛出了异常，但事务已提交（noRollbackFor）");
        }
        return Result.success("更新完成");
    }

    /**
     * 测试事务超时
     */
    @PostMapping("/timeout")
    public Result<String> testTimeout() {
        try {
            userService.longRunningOperation();
        } catch (Exception e) {
            return Result.error(500, "事务超时回滚: " + e.getMessage());
        }
        return Result.success("操作成功（未超时）");
    }
}