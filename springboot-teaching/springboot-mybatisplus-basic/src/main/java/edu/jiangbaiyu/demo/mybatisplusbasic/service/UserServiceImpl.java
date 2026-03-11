package edu.jiangbaiyu.demo.mybatisplusbasic.service;

import edu.jiangbaiyu.demo.mybatisplusbasic.entity.User;
import edu.jiangbaiyu.demo.mybatisplusbasic.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User create(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> list() {
        return userMapper.selectList(null);
    }

    @Override
    public User update(User user) {
        userMapper.updateById(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);
    }
}