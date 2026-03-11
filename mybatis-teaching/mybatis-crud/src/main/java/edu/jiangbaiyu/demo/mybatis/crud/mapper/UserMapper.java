package edu.jiangbaiyu.demo.mybatis.crud.mapper;

import edu.jiangbaiyu.demo.mybatis.crud.pojo.User;
import java.util.List;

public interface UserMapper {
    User selectUserById(int id);
    List<User> selectAllUsers();
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(int id);
}