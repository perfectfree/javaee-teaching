package edu.jiangbaiyu.demo.cloud.gatewayservice.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    //按客户端 IP 限流
    @Bean
//    @Primary
    public KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );
    }
//
//    //按请求路径限流
//    @Bean
//    public KeyResolver pathKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
//    }
//
//    //按用户 ID（从 Header 中获取）
//    @Bean
//    public KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst("X-User-Id"));
//    }
//
//    //按请求参数
//    @Bean
//    public KeyResolver paramKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
//    }
}