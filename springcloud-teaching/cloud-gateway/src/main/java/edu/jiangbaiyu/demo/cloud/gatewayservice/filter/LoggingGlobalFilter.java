package edu.jiangbaiyu.demo.cloud.gatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long start = System.currentTimeMillis();
        log.info("请求进入网关: {} {}", exchange.getRequest().getMethod(),
                exchange.getRequest().getURI());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long elapsed = System.currentTimeMillis() - start;
            log.info("请求结束: {} {}, 耗时: {} ms",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getURI(),
                    elapsed);
        }));
    }

    @Override
    public int getOrder() {
        return -1; // 优先级最高
    }
}