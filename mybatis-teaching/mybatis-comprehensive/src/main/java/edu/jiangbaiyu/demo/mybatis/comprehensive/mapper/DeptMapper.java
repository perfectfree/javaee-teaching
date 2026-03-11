package edu.jiangbaiyu.demo.mybatis.comprehensive.mapper;

import edu.jiangbaiyu.demo.mybatis.comprehensive.pojo.Dept;
import org.apache.ibatis.annotations.Select;

public interface DeptMapper {
    @Select("SELECT id, name FROM dept WHERE id = #{id}")
    Dept selectDeptById(int id);
}