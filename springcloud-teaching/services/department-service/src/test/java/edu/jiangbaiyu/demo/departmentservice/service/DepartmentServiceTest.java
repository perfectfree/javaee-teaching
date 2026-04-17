package edu.jiangbaiyu.demo.departmentservice.service;

import edu.jiangbaiyu.demo.cloud.common.exception.BusinessException;
import edu.jiangbaiyu.demo.cloud.departmentservice.App;
import edu.jiangbaiyu.demo.cloud.departmentservice.service.DepartmentService;
import edu.jiangbaiyu.demo.cloud.departmentservice.entity.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = App.class)
@Transactional  // 测试后自动回滚
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    void testAddDepartment_Success() {
        Department dept = new Department();
        dept.setName("研发中心");
        Department saved = departmentService.addDepartment(dept);
        assertNotNull(saved.getId());
        assertEquals("研发中心", saved.getName());
    }

    @Test
    void testAddDepartment_DuplicateName() {
        // 第一次添加
        Department dept1 = new Department();
        dept1.setName("市场部");
        departmentService.addDepartment(dept1);

        // 第二次添加同名部门，应抛异常
        Department dept2 = new Department();
        dept2.setName("市场部");
        assertThrows(BusinessException.class, () -> departmentService.addDepartment(dept2));
    }
}