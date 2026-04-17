package edu.jiangbaiyu.demo.cloud.departmentservice.controller;

import edu.jiangbaiyu.demo.cloud.common.result.R;
import edu.jiangbaiyu.demo.cloud.departmentservice.entity.Department;
import edu.jiangbaiyu.demo.cloud.departmentservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public R<Department> add(@RequestBody Department department) {
        return R.success(departmentService.addDepartment(department));
    }

    @GetMapping("/{id}")
    public R<Department> getById(@PathVariable("id") Long id) {
        return R.success(departmentService.getById(id));
    }
}