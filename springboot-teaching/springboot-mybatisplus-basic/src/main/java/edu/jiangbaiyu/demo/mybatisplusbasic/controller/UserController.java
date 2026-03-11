package edu.jiangbaiyu.demo.mybatisplusbasic.controller;

import edu.jiangbaiyu.demo.mybatisplusbasic.entity.User;
import edu.jiangbaiyu.demo.mybatisplusbasic.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public User create(@RequestBody User user) {
        userMapper.insert(user);
        return user;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userMapper.selectById(id);
    }

    @GetMapping
    public List<User> list() {
        return userMapper.selectList(null);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        userMapper.updateById(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userMapper.deleteById(id);
        return "删除成功";
    }
}