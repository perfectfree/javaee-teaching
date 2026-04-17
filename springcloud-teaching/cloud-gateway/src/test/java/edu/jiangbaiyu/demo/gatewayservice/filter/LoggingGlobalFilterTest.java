package edu.jiangbaiyu.demo.gatewayservice.filter;

import edu.jiangbaiyu.demo.cloud.gatewayservice.App;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = App.class)
@AutoConfigureWebTestClient
class LoggingGlobalFilterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testFilterLogging() {
        // 访问一个存在的路由（需确保服务已启动，或使用 Mock 路由）
        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk();  // 这里会触发过滤器，但日志输出无法断言，只能手动查看
    }
}