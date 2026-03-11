package edu.jiangbaiyu.demo.spring.thymeleaf.practice.service;

import edu.jiangbaiyu.demo.spring.thymeleaf.practice.entity.User;

import java.util.List;

/**
 * 用户服务接口
 * @author Robin
 */
public interface UserService {
    List<User> findAll();
    User findById(Integer id);
    User save(User user);
    User update(User user);
    void deleteById(Integer id);
}