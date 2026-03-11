package edu.jiangbaiyu.demo.spring.security.advanced.mapper;

import edu.jiangbaiyu.demo.spring.security.advanced.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户Mapper
 * @author Robin
 */
@Mapper
public interface UserMapper {

    @Select("SELECT id, username, password, roles, status, email FROM sys_user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);
}