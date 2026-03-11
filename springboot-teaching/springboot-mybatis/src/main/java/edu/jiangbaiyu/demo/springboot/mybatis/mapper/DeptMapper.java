package edu.jiangbaiyu.demo.springboot.mybatis.mapper;

import edu.jiangbaiyu.demo.springboot.mybatis.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 部门Mapper接口（使用注解方式）
 * @author Robin
 */
@Mapper
public interface DeptMapper {

    @Select("SELECT id, name FROM dept WHERE id = #{id}")
    Dept selectById(Integer id);
}