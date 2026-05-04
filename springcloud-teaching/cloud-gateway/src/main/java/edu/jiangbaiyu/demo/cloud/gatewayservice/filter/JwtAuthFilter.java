package edu.jiangbaiyu.demo.cloud.gatewayservice.filter;


import edu.jiangbaiyu.demo.cloud.common.utils.JwtUtil;
import edu.jiangbaiyu.demo.cloud.gatewayservice.config.URLWhitelistProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * JWT 鉴权全局过滤器
 * <p>
 * 实现 {@link GlobalFilter} 和 {@link Ordered} 接口，对进入网关的所有请求进行统一的 JWT 校验。
 * 白名单内的路径直接放行，其他请求必须携带有效的 Bearer Token 才能通过。
 * 校验通过后，将用户信息（如 userId、username）放入请求头，传递给下游微服务。
 * </p>
 */
@Slf4j  // Lombok 提供的日志注解，自动生成 log 对象
@Component // 将该类注册为 Spring 容器中的 Bean，使其能被网关自动发现
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil; // 自定义的 JWT 工具类，提供生成、校验、解析 Token 的方法

    /**
     * 白名单路径列表，从配置文件（如 application.yml）中注入
     * 配置项名称：custom-gateway.whitelist.paths
     * 示例：custom-gateway.whitelist.paths=/auth/login,/auth/register,/public/**
     */
    @Autowired
    private URLWhitelistProperties whitelistProperties;

    /**
     * Ant 风格的路径匹配器，用于判断请求路径是否匹配白名单中的模式
     * 支持通配符：? 匹配单字符，* 匹配零或多个字符，** 匹配多级路径
     */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 过滤器核心逻辑
     *
     * @param exchange 当前请求的上下文，包含 request、response 等信息
     * @param chain    过滤器链，用于将请求传递给下一个过滤器或目标服务
     * @return Mono<Void> 响应结果，表示处理完成
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取原始请求对象
        ServerHttpRequest request = exchange.getRequest();
        // 获取当前请求的路径（例如：/api/user/info）
        String path = request.getURI().getPath();

        // 1. 白名单放行：如果路径匹配白名单中的任一模式，直接进入下一个过滤器
        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }

        // 2. 获取 Authorization 头
        String authHeader = request.getHeaders().getFirst("Authorization");
        // 校验是否存在且以 "Bearer " 开头（注意空格）
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 未携带有效 Token，返回 401 Unauthorized
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete(); // 完成响应，不再继续传递请求
        }

        // 3. 提取 Token（去掉 "Bearer " 前缀）
        String token = authHeader.substring(7);
        // 使用 JwtUtil 校验 Token 的有效性（签名、过期时间等）
        if (!jwtUtil.validateToken(token)) {
            // Token 无效，返回 401
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 4. Token 有效，解析出用户信息
        Long userId = jwtUtil.getUserIdFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);

        // 5. 将用户信息放入请求头，传递给下游微服务
        // 注意：ServerHttpRequest 是不可变的，需要通过 mutate() 创建新实例
        ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-User-Id", String.valueOf(userId))   // 自定义头，下游可读取
                .header("X-Username", username)
                .build();

        // 用修改后的请求对象替换原请求，继续执行过滤器链
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    /**
     * 判断当前请求路径是否匹配白名单中的任意模式
     *
     * @param path 当前请求的路径
     * @return true 表示命中白名单，应直接放行；false 表示需要鉴权
     */
    private boolean isWhitelisted(String path) {
        // 使用 Stream API 遍历白名单，只要有一个模式匹配就返回 true
        return whitelistProperties.getPaths().stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    /**
     * 指定过滤器的执行顺序
     * 数值越小，优先级越高，越先执行
     * 此处设为 -100，确保该过滤器在大多数其他过滤器之前执行（如日志、限流等）
     *
     * @return 顺序值
     */
    @Override
    public int getOrder() {
        return -100;
    }
}