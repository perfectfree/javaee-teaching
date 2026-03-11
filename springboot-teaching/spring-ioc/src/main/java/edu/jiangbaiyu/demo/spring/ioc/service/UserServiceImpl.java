package edu.jiangbaiyu.demo.spring.ioc.service;

import org.springframework.stereotype.Service;

/**
 * 真实用户服务实现
 * @author Robin
 */
@Service  // 标记为Spring Bean，默认名称 userServiceImpl
public class UserServiceImpl implements UserService {

    @Override
    public String getUserInfo() {
        return "Real User: Zhang San (from database)";
    }
}