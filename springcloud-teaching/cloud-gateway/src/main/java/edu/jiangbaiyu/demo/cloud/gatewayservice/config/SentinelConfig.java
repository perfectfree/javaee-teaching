package edu.jiangbaiyu.demo.cloud.gatewayservice.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class SentinelConfig {

    @Bean
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler(List<ViewResolver> viewResolvers,
                                                                                     ServerCodecConfigurer serverCodecConfigurer) {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer) {
            @Override
            public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
                return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("{\"code\":429,\"msg\":\"请求过于频繁，请稍后再试\"}")
                        .flatMap(response -> response.writeTo(exchange, null));
            }
        };
    }
}