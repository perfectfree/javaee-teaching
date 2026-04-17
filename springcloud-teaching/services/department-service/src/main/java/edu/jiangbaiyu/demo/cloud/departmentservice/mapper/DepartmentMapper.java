package edu.jiangbaiyu.demo.cloud.departmentservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.jiangbaiyu.demo.cloud.departmentservice.entity.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
}