package edu.jiangbaiyu.demo.spring.mvc.rest.service;

import edu.jiangbaiyu.demo.spring.mvc.rest.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户服务实现（内存存储）
 * @author Robin
 */
@Service
public class UserServiceImpl implements UserService {

    // 模拟数据库：key=id, value=User对象
    private final Map<Integer, User> userMap = new ConcurrentHashMap<>();
    // 自增ID生成器
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public UserServiceImpl() {
        // 初始化一些测试数据
        save(new User(null, "张三", "zhangsan@example.com", 1));
        save(new User(null, "李四", "lisi@example.com", 1));
        save(new User(null, "王五", "wangwu@example.com", 0));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findById(Integer id) {
        return userMap.get(id);
    }

    @Override
    public User save(User user) {
        int newId = idGenerator.getAndIncrement();
        user.setId(newId);
        userMap.put(newId, user);
        return user;
    }

    @Override
    public User update(Integer id, User user) {
        if (!userMap.containsKey(id)) {
            return null;
        }
        user.setId(id);
        userMap.put(id, user);
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        userMap.remove(id);
    }
}