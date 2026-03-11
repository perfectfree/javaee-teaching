package edu.jiangbaiyu.demo.spring.thymeleaf.practice.mapper;

import edu.jiangbaiyu.demo.spring.thymeleaf.practice.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户Mapper
 * @author Robin
 */
@Mapper
public interface UserMapper {

    @Select("SELECT id, name, email, dept_id AS deptId, status FROM user")
    List<User> findAll();

    @Select("SELECT id, name, email, dept_id AS deptId, status FROM user WHERE id = #{id}")
    User findById(Integer id);

    @Insert("INSERT INTO user(name, email, dept_id, status) VALUES(#{name}, #{email}, #{deptId}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET name=#{name}, email=#{email}, dept_id=#{deptId}, status=#{status} WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(Integer id);
}