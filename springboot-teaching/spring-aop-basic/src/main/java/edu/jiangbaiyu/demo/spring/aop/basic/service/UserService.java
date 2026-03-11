package edu.jiangbaiyu.demo.spring.aop.basic.service;

/**
 * 用户服务接口（用于演示 JDK 动态代理）
 * @author Robin
 */
public interface UserService {
    String findUserById(Integer id);
    void saveUser(String name);
}