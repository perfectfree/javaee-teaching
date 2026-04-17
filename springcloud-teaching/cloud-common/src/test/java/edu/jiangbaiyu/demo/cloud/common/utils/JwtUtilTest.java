package edu.jiangbaiyu.demo.cloud.common.utils;

import edu.jiangbaiyu.demo.cloud.common.App;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = App.class)
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    private String token;
    private final Long userId = 100L;
    private final String username = "testuser";

    @BeforeEach
    void setUp() {
        token = jwtUtil.generateToken(userId, username);
    }

    @Test
    void testGenerateToken() {
        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3);
    }

    @Test
    void testParseToken() {
        Claims claims = jwtUtil.parseToken(token);
        assertEquals(userId, claims.get("userId", Long.class));
        assertEquals(username, claims.getSubject());
    }

    @Test
    void testGetUserIdFromToken() {
        Long parsedId = jwtUtil.getUserIdFromToken(token);
        assertEquals(userId, parsedId);
    }

    @Test
    void testGetUsernameFromToken() {
        String parsedName = jwtUtil.getUsernameFromToken(token);
        assertEquals(username, parsedName);
    }

    @Test
    void testValidateToken_Valid() {
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testValidateToken_Invalid() {
        assertFalse(jwtUtil.validateToken("invalid.token.string"));
    }
}