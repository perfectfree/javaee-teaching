package edu.jiangbaiyu.demo.cloud.departmentservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.jiangbaiyu.demo.cloud.common.exception.BusinessException;
import edu.jiangbaiyu.demo.cloud.departmentservice.entity.Department;
import edu.jiangbaiyu.demo.cloud.departmentservice.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> {

    @Transactional(rollbackFor = Exception.class)
    public Department addDepartment(Department department) {
        // 校验名称唯一性
        long count = this.lambdaQuery().eq(Department::getName, department.getName()).count();
        if (count > 0) {
            throw new BusinessException(400, "部门名称("+department.getName()+")已存在");
        }
        this.save(department);
//        int i = 1 / 0; // 模拟异常
        return department;
    }
}