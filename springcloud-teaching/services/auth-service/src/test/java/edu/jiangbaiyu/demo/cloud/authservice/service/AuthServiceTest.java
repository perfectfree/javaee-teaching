package edu.jiangbaiyu.demo.cloud.authservice.service;

import edu.jiangbaiyu.demo.cloud.authservice.App;
import edu.jiangbaiyu.demo.cloud.authservice.feign.UserFeignClient;
import edu.jiangbaiyu.demo.cloud.common.exception.BusinessException;
import edu.jiangbaiyu.demo.cloud.common.utils.JwtUtil;
import edu.jiangbaiyu.demo.cloud.common.utils.PasswordEncoderUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = App.class)
class AuthServiceTest {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Autowired
    private AuthService authService;

    @Test
    void testLogin_Success() {
        String token = authService.login("admin", "123456");

        assertNotNull(token);
        System.out.println("login(\"admin\", \"123456\") returned token: " + token);
    }

    @Test
    void testLogin_UserNotFound() {
        assertThrows(BusinessException.class, () -> authService.login("unknown", "123456"));
    }

    @Test
    void testLogin_WrongPassword() {
        assertThrows(BusinessException.class, () -> authService.login("admin", "wrong"));
    }
}