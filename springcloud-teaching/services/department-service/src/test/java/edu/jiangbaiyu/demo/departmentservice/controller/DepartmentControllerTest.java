package edu.jiangbaiyu.demo.departmentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jiangbaiyu.demo.cloud.departmentservice.App;
import edu.jiangbaiyu.demo.cloud.departmentservice.entity.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@Transactional
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Random rand = new Random();

    @Test
    void testAddDepartment() throws Exception {
        Department dept = new Department();
        dept.setName("测试部");
        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("测试部"))
                .andExpect(jsonPath("$.data.id").exists());
    }

    @Test
    void testGetDepartmentById() throws Exception {
        // 先新增一个部门
        Department dept = new Department();
        int rn = rand.nextInt(30);
        dept.setName("技术部"+rn);
        String response = mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andReturn().getResponse().getContentAsString();
        System.out.println("===========>>>>"+response);
        Long id = objectMapper.readTree(response).get("data").get("id").asLong();

        // 查询
        mockMvc.perform(get("/departments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value(dept.getName()));
    }

    @Test
    void testAddDepartment_DuplicateName() throws Exception {
        // 第一次新增
        Department dept = new Department();
        dept.setName("重复部门");
        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andExpect(status().isOk());

        // 第二次新增同名部门，应返回400
        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("部门名称("+dept.getName()+")已存在"));
    }
}