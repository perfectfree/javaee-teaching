package edu.jiangbaiyu.demo.mybatisplusbasic.service;


import edu.jiangbaiyu.demo.mybatisplusbasic.entity.User;

import java.util.List;

/**
 * 用户服务接口
 * @author Robin
 */
public interface UserService {

    User create(User user);

    User getById(Long id);

    List<User> list();

    User update(User user);

    void delete(Long id);
}