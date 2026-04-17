package edu.jiangbaiyu.demo.transactionservice.service;

import edu.jiangbaiyu.demo.transactionservice.entity.UserLog;
import edu.jiangbaiyu.demo.transactionservice.mapper.UserLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户日志服务层
 * 演示事务传播行为
 */
@Service
public class UserLogService {

    @Autowired
    private UserLogMapper userLogMapper;

    /**
     * 插入日志（默认事务传播：REQUIRED）
     * @param userId 用户ID
     * @param action 操作描述
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void log(Long userId, String action) {
        UserLog log = new UserLog(userId, action);
        userLogMapper.insert(log);
        // 模拟异常，测试事务回滚
        if ("error".equals(action)) {
            throw new RuntimeException("模拟日志记录异常，事务回滚");
        }
    }

    /**
     * 插入日志，使用 REQUIRES_NEW 传播行为
     * 无论外部事务是否回滚，该日志都会独立提交
     * @param userId 用户ID
     * @param action 操作描述
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logWithRequiresNew(Long userId, String action) {
        UserLog log = new UserLog(userId, action);
        userLogMapper.insert(log);
        // 也可以在这里抛出异常，测试外部事务不受影响
        if ("error_new".equals(action)) {
            throw new RuntimeException("模拟REQUIRES_NEW日志异常，该事务回滚但不影响外部");
        }
    }

    /**
     * 只读事务示例
     * @param userId 用户ID
     * @return 日志条数
     */
    @Transactional(readOnly = true)
    public long countLogsByUserId(Long userId) {
        return userLogMapper.selectList(null).stream()
                .filter(log -> log.getUserId().equals(userId))
                .count();
    }
}