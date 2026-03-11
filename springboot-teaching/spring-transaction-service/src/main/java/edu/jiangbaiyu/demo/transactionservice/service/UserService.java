package edu.jiangbaiyu.demo.transactionservice.service;

import edu.jiangbaiyu.demo.transactionservice.entity.User;
import edu.jiangbaiyu.demo.transactionservice.exception.BusinessException;
import edu.jiangbaiyu.demo.transactionservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务层
 * 演示声明式事务管理
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLogService userLogService;

    /**
     * 创建用户（无事务声明，默认每个数据库操作独立事务）
     */
    public User createUserWithoutTransaction(User user) {
        userMapper.insert(user);
        return user;
    }

    /**
     * 创建用户，并记录日志（使用事务，遇到任何异常回滚）
     */
    @Transactional(rollbackFor = Exception.class)
    public User createUserWithLog(User user) {
        userMapper.insert(user);
        // 记录日志，假设日志方法正常
        userLogService.log(user.getId(), "创建用户");
        return user;
    }

    /**
     * 创建用户，记录日志，但故意让日志抛出异常，观察事务回滚
     */
    @Transactional(rollbackFor = Exception.class)
    public User createUserWithLogAndError(User user) {
        userMapper.insert(user);
        // 记录日志，传入 "error" 触发异常
        userLogService.log(user.getId(), "error");
        return user;
    }

    /**
     * 创建用户，日志使用 REQUIRES_NEW，即使日志异常，用户创建也不回滚
     */
    @Transactional(rollbackFor = Exception.class)
    public User createUserWithIndependentLog(User user) {
        userMapper.insert(user);
        // 使用 REQUIRES_NEW 的日志方法，即使内部异常，外部事务不回滚（除非此处捕获异常）
        try {
            userLogService.logWithRequiresNew(user.getId(), "error_new");
        } catch (Exception e) {
            // 捕获内部异常，外部事务正常提交
            System.out.println("日志记录失败，但用户创建成功: " + e.getMessage());
        }
        return user;
    }

    /**
     * 只读事务：根据ID查询用户
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    /**
     * 演示 noRollbackFor：指定某些异常不回滚
     */
    @Transactional(noRollbackFor = {IllegalArgumentException.class})
    public void updateUserWithNoRollback(User user) {
        userMapper.updateById(user);
        // 抛出指定异常，事务不回滚
        throw new IllegalArgumentException("更新用户时参数异常，但事务不回滚（演示noRollbackFor）");
    }

    /**
     * 演示超时事务（timeout）
     */
    @Transactional(timeout = 1)
    public void longRunningOperation() {
        // 模拟耗时操作，超过1秒将抛出异常
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // 此处不会执行到，因为超时已回滚
    }
}