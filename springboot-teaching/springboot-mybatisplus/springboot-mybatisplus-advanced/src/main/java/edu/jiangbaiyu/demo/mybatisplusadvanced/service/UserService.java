package edu.jiangbaiyu.demo.mybatisplusadvanced.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.jiangbaiyu.demo.mybatisplusadvanced.entity.User;
import edu.jiangbaiyu.demo.mybatisplusadvanced.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户服务层
 * 封装业务逻辑，演示条件构造器、分页等高级特性
 * @author Robin
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // ========== 插入 ==========
    public User addUser(User user) {
        userMapper.insert(user);
        return user;
    }

    // ========== 条件查询 ==========
    public List<User> searchUsers(String name, Integer minAge, Integer maxAge, String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), User::getName, name)
                .ge(minAge != null, User::getAge, minAge)
                .le(maxAge != null, User::getAge, maxAge)
                .eq(StringUtils.hasText(email), User::getEmail, email);
        return userMapper.selectList(wrapper);
    }

    // ========== 分页查询 ==========
    public Page<User> getUsersPage(int current, int size, String name) {
        Page<User> page = new Page<>(current, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), User::getName, name);
        return userMapper.selectPage(page, wrapper);
    }

    // ========== 更新 ==========
    public User updateUser(User user) {
        userMapper.updateById(user);
        return user;
    }

    // ========== 删除（逻辑删除） ==========
    public void deleteUser(Long id) {
        userMapper.deleteById(id);  // 实际执行 update set deleted=1 where id=?
    }

    // ========== 查询所有（包括逻辑删除的数据，用于演示） ==========
    public List<User> listAllIncludeDeleted() {
        // 使用条件构造器，不自动过滤逻辑删除
        return userMapper.selectList(new LambdaQueryWrapper<>());
    }
}