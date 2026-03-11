package edu.jiangbaiyu.demo.mybatis.advanced.mapper;

import edu.jiangbaiyu.demo.mybatis.advanced.pojo.Dept;
import org.apache.ibatis.annotations.Select;

public interface DeptMapper {
    @Select("SELECT id, name FROM dept WHERE id = #{id}")
    Dept selectDeptById(int id);
}