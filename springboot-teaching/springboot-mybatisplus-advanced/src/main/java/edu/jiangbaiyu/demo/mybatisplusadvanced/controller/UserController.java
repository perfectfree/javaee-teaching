package edu.jiangbaiyu.demo.mybatisplusadvanced.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.jiangbaiyu.demo.mybatisplusadvanced.entity.User;
import edu.jiangbaiyu.demo.mybatisplusadvanced.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    // 条件查询
    @GetMapping("/search")
    public List<User> search(@RequestParam(required = false) String name,
                             @RequestParam(required = false) Integer age) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), User::getName, name)
                .eq(age != null, User::getAge, age);
        return userMapper.selectList(wrapper);
    }

    // 分页查询
    @GetMapping("/page")
    public Page<User> page(@RequestParam(defaultValue = "1") int current,
                           @RequestParam(defaultValue = "5") int size) {
        Page<User> page = new Page<>(current, size);
        return userMapper.selectPage(page, null);
    }

    // 其他CRUD方法...
}