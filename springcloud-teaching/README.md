### 整体演示顺序建议

| 序号 | 演示内容                                                     | 使用模块                    |
| :--- | :----------------------------------------------------------- | :-------------------------- |
| 1    | **部门管理模块整合（Controller-Service-Mapper + 事务）**<br/>\- 创建数据库表，配置数据源<br/>\- 编写实体类、Mapper、Service、Controller<br/>\- 在 Service 中添加 `@Transactional`，演示事务回滚<br/>\- 编写统一响应类 `R<T>` 和全局异常处理<br/>\- 测试新增部门（正常、重复、异常场景） | department-service          |
| 2    | **Nacos 安装启动 + user-service 注册**<br/>\- 下载并启动 Nacos Server（standalone 模式）<br/>\- 访问 Nacos 控制台，验证启动成功<br/>\- 在 user-service 中添加 Nacos Discovery 依赖<br/>\- 配置 `bootstrap.yml`，指定服务名和 Nacos 地址<br/>\- 启动 user-service，在控制台查看服务注册成功<br/>\- 启动多实例，观察实例列表变化 | user-service                |
| 3    | **OpenFeign 调用（order-service 调用 user-service）**<br/>\- 在 order-service 中添加 OpenFeign 依赖<br/>\- 编写 Feign 客户端接口，使用 `@FeignClient` 指定服务名<br/>\- 在启动类添加 `@EnableFeignClients`<br/>\- 在 Controller 中注入 Feign 客户端并调用<br/>\- 测试正常调用（用户存在）<br/>\- 测试异常场景（用户不存在、服务不可用）<br/>\- 启动多个 user-service 实例，观察负载均衡效果 | order-service、user-service |
| 4    | **Nacos Config 动态刷新**<br/>\- 在 Nacos 控制台创建配置文件（Data ID: `user-service-dev.yaml`）<br/>\- 在 user-service 中添加 Nacos Config 依赖<br/>\- 配置 `bootstrap.yml`，启用配置中心<br/>\- 在 Controller 中使用 `@Value` 注入配置项，添加 `@RefreshScope`<br/>\- 启动服务，验证配置加载成功<br/>\- 修改 Nacos 中的配置，调用接口验证动态刷新 | user-service（配置中心）    |
| 5    | **Gateway 静态路由 + 集成 Nacos 动态路由**<br/>\- 创建 gateway-service 模块，引入 Gateway 依赖<br/>\- 配置静态路由（硬编码 URI），测试转发功能<br/>\- 添加 Nacos Discovery 依赖，将网关注册到 Nacos<br/>\- 修改路由配置为 `lb://service-name`，实现动态路由<br/>\- 启动多个服务实例，测试负载均衡效果 | gateway-service + 各服务    |
| 6    | **Gateway 自定义全局过滤器（日志记录）**<br/>\- 实现 `GlobalFilter` 和 `Ordered` 接口<br/>\- 在 `filter` 方法中记录请求开始时间、路径、方法<br/>\- 在 `chain.filter` 之后记录耗时并打印日志<br/>\- 配置 `getOrder()` 控制执行顺序<br/>\- 测试访问，观察控制台日志输出 | gateway-service             |
| 7    | **Gateway 集成 Sentinel 限流**<br/>\- 启动 Sentinel Dashboard<br/>\- 在网关配置 Sentinel<br/>\- 为某个路由（如 `/users/**`）设置 QPS=1<br/>\- 快速访问触发限流，观察自定义返回 | gateway-service             |
| 8    | **服务接口 QPS 限流**<br/>\- user-service 引入 Sentinel<br/>\- 在 Controller 方法上使用 `@SentinelResource`<br/>\- Dashboard 中为接口配置 QPS 限流<br/>\- 快速访问触发 blockHandler | user-service                |
| 9    | **熔断降级（慢调用比例）**<br/>\- 在 user-service 模拟慢方法<br/>\- Dashboard 配置熔断规则<br/>\- 请求触发熔断后进入 fallback | user-service                |
| 10   | **Feign 整合 Sentinel 降级**<br/>\- order-service 开启 `feign.sentinel.enabled`<br/>\- 为 Feign 客户端指定 fallback 类<br/>\- 停止 user-service，调用接口验证降级返回 | order-service               |
| 11   | **JWT 认证服务开发**<br/>\- 创建 auth-service，引入 cloud-common 中的 JwtUtil<br/>\- 通过 Feign 调用 user-service 获取用户信息（含加密密码）<br/>\- 实现 `/auth/login`，返回 JWT Token | auth-service、user-service  |
| 12   | **网关统一鉴权集成**<br/>\- 在 gateway-service 中编写 JWT 鉴权全局过滤器<br/>\- 配置白名单（如 `/auth/login`）<br/>\- 将解析的用户信息通过请求头传递给下游服务<br/>\- 测试携带/不携带 Token 的访问效果 | gateway-service、各业务服务 |

#### 一次性启动所有服务（演示环境）

1. 启动 Nacos Server
2. 启动Sentinel Dashboard (修改端口: `java -Dserver.port=8079`)
3. 启动 MySQL（各库建表）
4. 启动 `department-service`（8080）
5. 启动 `user-service`（8081）
6. 启动 `order-service`（8082）
7. 启动 `gateway-service`（8083）

所有服务注册成功后，通过网关即可访问所有微服务，观察日志、事务回滚、Feign 调用、配置刷新、过滤器等效果。