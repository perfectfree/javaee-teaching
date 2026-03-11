package edu.jiangbaiyu.demo.mybatis.comprehensive.mapper;

import edu.jiangbaiyu.demo.mybatis.comprehensive.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    // 基础CRUD使用注解
    @Select("SELECT id, name, email, dept_id AS deptId, status FROM user WHERE id = #{id}")
    User selectUserById(int id);

    @Select("SELECT id, name, email, dept_id AS deptId, status FROM user")
    List<User> selectAllUsers();

    @Insert("INSERT INTO user (name, email, dept_id, status) VALUES (#{name}, #{email}, #{deptId}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    @Update("UPDATE user SET name = #{name}, email = #{email}, dept_id = #{deptId}, status = #{status} WHERE id = #{id}")
    int updateUser(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteUser(int id);

    // 复杂功能使用XML映射
    List<User> searchUsers(@Param("name") String name,
                           @Param("email") String email,
                           @Param("status") Integer status,
                           @Param("deptId") Integer deptId);

    int deleteUsersByIds(@Param("ids") List<Integer> ids);

    List<User> selectUsersWithDept();
}