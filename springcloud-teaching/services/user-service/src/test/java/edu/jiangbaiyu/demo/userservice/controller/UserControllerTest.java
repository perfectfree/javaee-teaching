package edu.jiangbaiyu.demo.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jiangbaiyu.demo.cloud.userservice.App;
import edu.jiangbaiyu.demo.cloud.userservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddUser() throws Exception {
        User user = new User();
        user.setName("测试用户");
        user.setPassword("123456");
        user.setEmail("test@example.com");
        user.setAge(20);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("测试用户"))
                .andExpect(jsonPath("$.data.id").exists());
    }

    @Test
    void testGetUserById() throws Exception {
        // 先创建用户
        User user = new User();
        user.setName("查询用户");
        user.setPassword("123456");
        user.setEmail("query@example.com");
        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(response).get("data").get("id").asLong();

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value("查询用户"));
    }
}