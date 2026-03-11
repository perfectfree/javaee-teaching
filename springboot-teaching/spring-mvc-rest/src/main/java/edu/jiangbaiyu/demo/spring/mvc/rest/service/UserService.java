package edu.jiangbaiyu.demo.spring.mvc.rest.service;

import edu.jiangbaiyu.demo.spring.mvc.rest.entity.User;
import java.util.List;

/**
 * 用户服务接口
 * @author Robin
 */
public interface UserService {
    List<User> findAll();
    User findById(Integer id);
    User save(User user);
    User update(Integer id, User user);
    void deleteById(Integer id);
}