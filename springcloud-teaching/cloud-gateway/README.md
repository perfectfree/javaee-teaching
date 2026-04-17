**模块作用**：作为微服务架构的统一入口，演示路由转发、集成 Nacos 实现动态路由、自定义全局过滤器。

#### 准备步骤

1. 确保 department-service、user-service、order-service 均已注册到 Nacos 并启动。
2. 修改 `cloud--gateway/src/main/resources/application.yml` 中的 Nacos 地址（若与默认不同）。
3. 启动 cloud-gateway（默认端口 `8083`）。

#### 演示内容及预期结果

**基础路由**

- 通过网关访问部门服务：

  ```
  GET http://localhost:8083/departments/1
  ```

  请求应被转发到 `department-service` 的 `/departments/1`，返回部门信息。

- 通过网关访问用户服务：

  ```
  GET http://localhost:8083/users/1
  ```

  请求应被转发到 `user-service` 的 `/users/1`。

- 通过网关创建订单（通过网关提交）：

  ```
  POST http://localhost:8083/orders
  Body: {"userId":1,"product":"手机","amount":3999.00}
  ```

  请求应被转发到 `order-service` 的 `/orders`，创建订单。

- 修改 `uri` 为 `lb://` 并启动多个提供者实例，多次请求观察负载均衡。

**自定义全局过滤器**

- 访问任意网关路由，查看控制台日志，会打印请求进入和结束的耗时信息（由 `LoggingGlobalFilter` 实现）。
- 可尝试修改 `getOrder()` 返回值，观察执行顺序变化。

**内置过滤器演示**

- 在路由配置中添加 `filters`：

  ```
  filters:
    - AddRequestHeader=X-Request-Source, gateway
  ```

  重启网关，在 department-service 的 Controller 中获取该请求头，确认添加成功。

#### 教学关键点

- **网关作用**：统一入口、路由转发、过滤器链（鉴权、日志、限流）。
- **动态路由**：通过 `lb://` 集成注册中心，实现服务发现与负载均衡。
- **过滤器链**：全局过滤器对所有路由生效，可控制执行顺序。

#### 常见问题

- 路由不匹配：检查 `predicates` 中的 `Path` 是否与请求路径一致，注意前缀（如 `/departments/**` 会匹配 `/departments/1`）。
- 转发后路径错误：若后端服务接口路径带有上下文（如 `/api/users`），可能需要使用 `StripPrefix` 过滤器去除前缀。
- 无法启动：确保没有同时引入 `spring-boot-starter-web`，Gateway 基于 WebFlux，与 Web 冲突。