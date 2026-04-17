package edu.jiangbaiyu.demo.cloud.common.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordEncoderUtilTest {

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Test
    void testEncodeAndMatches() {
        String rawPassword = "123456";
        String encoded = passwordEncoderUtil.encode(rawPassword);
        assertNotEquals(rawPassword, encoded);
        assertTrue(passwordEncoderUtil.matches(rawPassword, encoded));
        assertFalse(passwordEncoderUtil.matches("wrong", encoded));
    }
}