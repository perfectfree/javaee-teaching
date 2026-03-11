package edu.jiangbaiyu.demo.spring.thymeleaf.practice.service;

import edu.jiangbaiyu.demo.spring.thymeleaf.practice.entity.User;
import edu.jiangbaiyu.demo.spring.thymeleaf.practice.mapper.UserMapper;
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
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public User save(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User update(User user) {
        userMapper.update(user);
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }
}