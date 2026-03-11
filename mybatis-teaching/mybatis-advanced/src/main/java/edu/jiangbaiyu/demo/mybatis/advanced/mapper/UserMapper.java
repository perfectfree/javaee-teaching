package edu.jiangbaiyu.demo.mybatis.advanced.mapper;

import edu.jiangbaiyu.demo.mybatis.advanced.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    // ========== 基础 CRUD ==========
    User selectUserById(int id);
    List<User> selectAllUsers();
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(int id);

    // ========== 动态 SQL：多条件查询 ==========
    List<User> searchUsers(@Param("name") String name,
                           @Param("email") String email,
                           @Param("status") Integer status,
                           @Param("deptId") Integer deptId);

    // ========== 批量操作 ==========
    int deleteUsersByIds(@Param("ids") List<Integer> ids);

    // ========== 关联查询（一对一） ==========
    List<User> selectUsersWithDept();
}