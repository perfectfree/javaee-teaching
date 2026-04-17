## order-service 模块（OpenFeign 服务间通信）

**模块作用**：作为服务消费者，演示通过 OpenFeign 声明式调用 user-service 的接口，并展示负载均衡效果。

**演示目标**：
在 order-service 中通过 OpenFeign 声明式调用 user-service 的接口，展示参数传递、响应封装和负载均衡。

**关键操作**：

1. **准备 user-service 数据**

   - 确保 user-service 已注册到 Nacos 并启动（端口 8820、8821）
   - 通过 user-service 创建测试用户：

   ```bash
   curl -X POST http://localhost:8820/users \
     -H "Content-Type: application/json" \
     -d '{"name":"张三","age":25,"email":"zhangsan@example.com","password":"123456"}'
   ```

2. **配置 order-service**

   - 在 `pom.xml` 中添加 OpenFeign 依赖：

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-openfeign</artifactId>
   </dependency>
   ```

   - 在启动类添加 `@EnableFeignClients`
   - 在 `bootstrap.yml` 中配置 Feign 日志：

   ```yaml
   logging:
     level:
       edu.jiangbaiyu.demo.orderservice.feign: DEBUG
   ```

3. **编写 Feign 客户端**

   ```java
   @FeignClient(name = "user-service")
   public interface UserFeignClient {
       @GetMapping("/users/{id}")
       R<UserDTO> getUserById(@PathVariable("id") Long id);
   }
   ```

4. **在 Controller 中注入并使用**

   ```java
   @Autowired
   private UserFeignClient userFeignClient;
   
   @PostMapping
   public R<Order> create(@RequestBody Order order) {
       R<UserDTO> userResp = userFeignClient.getUserById(order.getUserId());
       if (userResp.getCode() != 200 || userResp.getData() == null) {
           return R.error(400, "用户不存在");
       }
       // 创建订单逻辑...
   }
   ```

5. **启动 order-service**

   - 启动 `OrderServiceApplication`（默认端口 8082）

6. **测试 Feign 调用（正常场景）**

   - 创建订单：`POST http://localhost:8082/orders`
   - 请求体：`{"userId":1,"product":"笔记本电脑","amount":5999.00}`
   - 预期返回 `200`，包含订单信息
   - 观察 order-service 控制台，应打印 Feign 请求的详细日志

7. **测试异常场景**

   - 使用不存在的 userId（如 999）创建订单
   - 预期返回 `400`，错误信息“用户不存在”

8. **负载均衡演示**

   - 启动两个 user-service 实例（8081、8084）
   - 多次调用创建订单接口
   - 观察两个 user-service 控制台日志，请求被轮询分发（Ribbon 默认轮询策略）

**预期结果**：

- 订单创建成功时返回订单数据
- Feign 调用日志清晰显示请求 URL、响应内容
- 多实例时负载均衡生效，请求均匀分布
- 校验失败时返回业务错误信息

**教学要点**：

- **声明式调用**：`@FeignClient` 将远程调用封装成本地接口，降低复杂度
- **接口契约**：Feign 方法签名必须与被调服务 Controller 严格匹配（路径、参数、返回值）
- **负载均衡**：Feign 集成 Ribbon，默认轮询策略
- **契约精神**：接口设计是团队协作的基础，引导学生树立“契约精神”