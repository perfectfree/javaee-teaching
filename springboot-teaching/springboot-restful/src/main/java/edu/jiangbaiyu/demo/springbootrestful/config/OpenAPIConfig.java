package edu.jiangbaiyu.demo.springbootrestful.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API进阶演示")
                        .description("课次4：状态码、全局异常、Swagger集成")
                        .version("1.0")
                        .contact(new Contact().name("Robin").email("robin@example.com")));
    }
}