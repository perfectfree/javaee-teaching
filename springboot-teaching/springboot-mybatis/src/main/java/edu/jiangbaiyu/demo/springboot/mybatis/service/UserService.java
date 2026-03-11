package edu.jiangbaiyu.demo.springboot.mybatis.service;

import edu.jiangbaiyu.demo.springboot.mybatis.entity.User;

import java.util.List;

/**
 * 用户服务接口
 * @author Robin
 */
public interface UserService {
    User findById(Integer id);
    List<User> findAll();
    User create(User user);
    User update(User user);
    void deleteById(Integer id);
    List<User> search(String name, String email, Integer status, Integer deptId);
    List<User> findUsersWithDept();

    // 演示事务的方法
    void transferUserDept(Integer userId, Integer newDeptId);
}