## 1. 启动应用

执行 `edu.jiangbaiyu.demo.springbootcore.App` 的 `main` 方法。

### 2. 访问应用

- 默认端口：`8080`（可通过配置文件修改）
- 无默认 Web 页面，所有功能通过 REST API 暴露。

------

## 3. 演示内容与步骤

### 3.1 自动配置原理演示

#### 3.1.1 查看自动配置报告（通过 debug=true）

- **操作**：在 `application.yml` 中设置 `debug: true`（已在配置文件中启用）。
- **重启应用**，观察控制台输出。启动后，控制台会打印两部分内容：
  - `Positive matches`：已启用的自动配置类（如 `DispatcherServletAutoConfiguration`, `WebMvcAutoConfiguration` 等）。
  - `Negative matches`：未启用的自动配置类及原因（例如某个条件注解不满足）。
- **学习点**：解释 Spring Boot 如何通过 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`（Spring Boot 3 中）或 `spring.factories` 加载自动配置类，并结合 `@Conditional` 系列注解按需装配。

#### 3.1.2 通过 Actuator 端点查看（可选）

- 确保 `spring-boot-starter-actuator` 已引入（已在 pom.xml 中包含）。

- 访问 `http://localhost:8080/actuator/conditions`（需添加配置 `management.endpoints.web.exposure.include=conditions`，但默认未开放，可快速演示在配置文件中添加）：

  查看 `application.yml` 文件，包含：

  ```
  management:
    endpoints:
      web:
        exposure:
          include: conditions
  ```

  

- 重启后，访问上述 URL 可获得 JSON 格式的自动配置报告。

- **学习点**：展示 Actuator 提供的生产级监控能力。

### 3.2 起步依赖与配置管理演示

#### 3.2.1 对比传统 SSM 与 Spring Boot starter

- **讲解**：传统 SSM 需要手动管理大量依赖（Spring, Spring MVC, MyBatis 等），版本冲突风险高。Spring Boot 的 starter 聚合了一组兼容的依赖，例如 `spring-boot-starter-web` 包含了 Spring MVC、Tomcat、Jackson 等，简化依赖管理。
- **演示**：查看 `pom.xml`，仅引入一个 `spring-boot-starter-web` 即可构建 Web 应用。

#### 3.2.2 多环境配置演示

- 查看 `application.yml` 文件，包含多环境配置：

  ```
  spring:
    profiles:
      active: dev
  ---
  spring:
    config:
      activate:
        on-profile: dev
  server:
    port: 8080
  ---
  spring:
    config:
      activate:
        on-profile: prod
  server:
    port: 8081
  ```

  

- **操作1**：保持 `spring.profiles.active=dev`，启动应用，观察端口为 8080。

- **操作2**：修改 `active: prod`，重启应用，端口变为 8081，验证配置切换成功。

- **教学点**：解释不同环境（开发、测试、生产）可通过配置文件隔离，避免硬编码。

### 3.3 RESTful API 开发实战

#### 3.3.1 创建用户（POST /users）

- **URL**：`http://localhost:8080/users`

- **方法**：POST

- **请求头**：`Content-Type: application/json`

- **请求体**（JSON）：

  ```
  {
    "name": "张三",
    "age": 25
  }
  ```

  

- **预期响应**（200 OK）：

  ```
  {
    "id": 1,
    "name": "张三",
    "age": 25
  }
  ```

  

- **观察**：服务端自动生成 ID（基于 `AtomicLong`），返回创建的用户对象。

#### 3.3.2 查询所有用户（GET /users）

- **URL**：`http://localhost:8080/users`

- **方法**：GET

- **预期响应**（200 OK）：

  ```
  {
    "1": { "id": 1, "name": "张三", "age": 25 },
    "2": { "id": 2, "name": "李四", "age": 30 }
  }
  ```

  **观察**：返回一个 Map，键为用户 ID，值为用户对象。

#### 3.3.3 根据 ID 查询用户（GET /users/{id}）

- **URL**：`http://localhost:8080/users/1`

- **方法**：GET

- **预期响应**（200 OK）：

  ```
  {
    "id": 1,
    "name": "张三",
    "age": 25
  }
  ```

  **若 ID 不存在**：返回 `null`（可自行添加业务逻辑抛异常，但本模块简单返回 null）。

#### 3.3.4 更新用户（PUT /users/{id}）

- **URL**：`http://localhost:8080/users/1`

- **方法**：PUT

- **请求体**：

  ```
  {
    "name": "张三（更新）",
    "age": 26
  }
  ```

  

- **预期响应**（200 OK）：

  ```
  {
    "id": 1,
    "name": "张三（更新）",
    "age": 26
  }
  ```

  

- **观察**：更新后数据与请求体一致。

#### 3.3.5 删除用户（DELETE /users/{id}）

- **URL**：`http://localhost:8080/users/1`

- **方法**：DELETE

- **预期响应**（200 OK）：

  ```
  删除成功
  ```

  

- **观察**：再次查询 ID=1 返回 null。

#### 3.3.6 参数绑定演示（搜索接口）

在 `UserController` 中存在一个搜索方法（需检查是否存在，若不存在可临时添加或使用已有）：

```
@GetMapping("/search")
public User searchUser(@RequestParam(required = false) String name,
                       @RequestParam(defaultValue = "0") Integer age) {
    // 模拟：返回第一个匹配名称的用户
    return userMap.values().stream()
            .filter(u -> name != null && name.equals(u.getName()))
            .findFirst().orElse(null);
}
```



- **URL**：`http://localhost:8080/users/search?name=张三&age=25`
- **方法**：GET
- **预期响应**：返回匹配的用户（若有）或 null。
- **演示点**：
  - `required=false` 表示参数可选。
  - `defaultValue="0"` 演示默认值。
  - 尝试不传 `age` 参数，观察 age 默认为 0。

------

## 4. 课堂练习建议

1. **练习1**：为 `User` 添加 `email` 字段，修改所有接口支持该字段。
2. **练习2**：在 `application.yml` 中添加一个新的环境（如 `test`），端口设为 8082，并验证切换。
3. **练习3**：查看自动配置报告，找到 `JdbcTemplateAutoConfiguration` 未启用的原因（提示：缺少 `DataSource` 相关类）。
4. **练习4**：尝试在 `UserController` 中添加一个接口，接收 `@RequestParam` 和 `@PathVariable` 混合参数，例如 `GET /users/{id}/detail?fields=name,age`。

------

## 5. 常见问题排查

- **应用启动失败**：检查端口是否被占用，修改 `server.port` 重试。
- **自动配置报告不打印**：确认 `debug: true` 设置在 `application.yml` 中，且缩进正确。
- **接口返回 404**：检查 Controller 类是否有 `@RestController` 或 `@Controller`，以及 `@RequestMapping` 路径是否正确。
- **JSON 解析错误**：检查请求头是否包含 `Content-Type: application/json`，以及请求体格式是否正确。