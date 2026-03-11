package edu.jiangbaiyu.demo.spring.ioc.service;

import org.springframework.stereotype.Service;

/**
 * 模拟用户服务实现（用于演示@Qualifier）
 * @author Robin
 */
@Service("mockUserService")  // 指定bean名称为 mockUserService
public class UserServiceMockImpl implements UserService {

    @Override
    public String getUserInfo() {
        return "Mock User: Li Si (for testing)";
    }
}