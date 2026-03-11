package edu.jiangbaiyu.demo.mybatis.annotation.mapper;

import edu.jiangbaiyu.demo.mybatis.annotation.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserAnnotationMapper {
    @Select("SELECT id, name, email FROM user WHERE id = #{id}")
    User selectUserById(int id);

    @Select("SELECT id, name, email FROM user")
    List<User> selectAllUsers();

    @Insert("INSERT INTO user (name, email) VALUES (#{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    @Update("UPDATE user SET name = #{name}, email = #{email} WHERE id = #{id}")
    int updateUser(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteUser(int id);
}