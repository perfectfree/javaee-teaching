package edu.jiangbaiyu.demo.gatewayservice.filter;

import edu.jiangbaiyu.demo.cloud.common.utils.JwtUtil;
import edu.jiangbaiyu.demo.cloud.gatewayservice.App;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = App.class)
class JwtAuthFilterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JwtUtil jwtUtil;

    private String validToken;
    private final Long userId = 1L;

    @Test
    void testAccessWithValidToken() {
        webTestClient.get().uri("/users/1")
                .header("Authorization", "Bearer " + validToken)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testAccessWithoutToken() {
        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testAccessWithInvalidToken() {
        when(jwtUtil.validateToken("invalid-token")).thenReturn(false);
        webTestClient.get().uri("/users/1")
                .header("Authorization", "Bearer invalid-token")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testWhitelistPath() {
        // 白名单路径应直接放行，即使没有 token
        webTestClient.get().uri("/auth/login")
                .exchange()
                .expectStatus().isOk();  // 实际可能返回 404，但应不会返回 401
    }
}