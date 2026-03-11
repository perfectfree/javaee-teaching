package edu.jiangbaiyu.demo.springboot.mybatis.mapper;

import edu.jiangbaiyu.demo.springboot.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口（混合使用注解和XML）
 * @author Robin
 */
@Mapper
public interface UserMapper {

    // 基础CRUD（注解方式简单操作）
    @org.apache.ibatis.annotations.Select("SELECT id, name, email, dept_id AS deptId, status FROM user WHERE id = #{id}")
    User selectById(Integer id);

    @org.apache.ibatis.annotations.Select("SELECT id, name, email, dept_id AS deptId, status FROM user")
    List<User> selectAll();

    @org.apache.ibatis.annotations.Insert("INSERT INTO user(name, email, dept_id, status) VALUES(#{name}, #{email}, #{deptId}, #{status})")
    @org.apache.ibatis.annotations.Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @org.apache.ibatis.annotations.Update("UPDATE user SET name=#{name}, email=#{email}, dept_id=#{deptId}, status=#{status} WHERE id=#{id}")
    int update(User user);

    @org.apache.ibatis.annotations.Delete("DELETE FROM user WHERE id=#{id}")
    int deleteById(Integer id);

    // 动态SQL查询（使用XML映射）
    List<User> searchUsers(@Param("name") String name,
                           @Param("email") String email,
                           @Param("status") Integer status,
                           @Param("deptId") Integer deptId);

    // 关联查询用户及部门信息（使用XML resultMap）
    List<User> selectUsersWithDept();
}