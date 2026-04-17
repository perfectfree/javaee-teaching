package edu.jiangbaiyu.demo.cloud.authservice.controller;

import edu.jiangbaiyu.demo.cloud.authservice.App;
import edu.jiangbaiyu.demo.cloud.authservice.feign.UserFeignClient;
import edu.jiangbaiyu.demo.cloud.common.utils.JwtUtil;
import edu.jiangbaiyu.demo.cloud.common.utils.PasswordEncoderUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .param("username", "admin")
                        .param("password", "123456")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    void testLogin_WrongPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .param("username", "admin")
                        .param("password", "wrong")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("密码错误"));
    }
}