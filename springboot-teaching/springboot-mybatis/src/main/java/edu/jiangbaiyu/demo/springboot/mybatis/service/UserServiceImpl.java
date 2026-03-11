package edu.jiangbaiyu.demo.springboot.mybatis.service;

import edu.jiangbaiyu.demo.springboot.mybatis.entity.User;
import edu.jiangbaiyu.demo.springboot.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现
 * @author Robin
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    @Transactional  // 事务管理
    public User create(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        userMapper.update(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public List<User> search(String name, String email, Integer status, Integer deptId) {
        return userMapper.searchUsers(name, email, status, deptId);
    }

    @Override
    public List<User> findUsersWithDept() {
        return userMapper.selectUsersWithDept();
    }

    /**
     * 演示事务回滚：将用户部门转移，并模拟异常
     * 该方法会先更新用户部门，然后故意抛出异常，验证事务回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)  // 所有异常都回滚
    public void transferUserDept(Integer userId, Integer newDeptId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 更新部门
        user.setDeptId(newDeptId);
        userMapper.update(user);

        // 模拟后续操作异常（例如远程调用失败）
        if (newDeptId == 999) {
            throw new RuntimeException("模拟异常，事务应该回滚");
        }

        // 正常情况不会抛出异常
    }
}