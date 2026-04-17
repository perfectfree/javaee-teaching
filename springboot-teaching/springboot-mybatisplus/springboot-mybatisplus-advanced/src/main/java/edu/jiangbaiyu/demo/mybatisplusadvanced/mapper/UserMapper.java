package edu.jiangbaiyu.demo.mybatisplusadvanced.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.jiangbaiyu.demo.mybatisplusadvanced.entity.User;

public interface UserMapper extends BaseMapper<User> {
    // 无需写任何方法，继承BaseMapper即拥有CRUD
}