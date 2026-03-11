package edu.jiangbaiyu.demo.spring.aop.basic.service;

import org.springframework.stereotype.Service;

/**
 * 用户服务实现（有接口，默认使用 JDK 动态代理）
 * @author Robin
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String findUserById(Integer id) {
        System.out.println("【UserServiceImpl】执行 findUserById，id=" + id);
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id 必须大于0");
        }
        return "User-" + id;
    }

    @Override
    public void saveUser(String name) {
        System.out.println("【UserServiceImpl】执行 saveUser，name=" + name);
    }
}