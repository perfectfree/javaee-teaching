## 1. 启动应用

- 运行主类：`edu.jiangbaiyu.demo.springbootrestful.App`
- 应用端口：`8080`（可查看控制台输出）
- Swagger UI地址：http://localhost:8080/swagger-ui.html

------

## 2. Swagger UI 体验

### 2.1 访问 Swagger UI

- **操作**：在浏览器中打开 http://localhost:8080/swagger-ui.html
- **观察**：
  - 页面显示所有接口分组（如 `user-controller`）
  - 每个接口的 HTTP 方法、路径、参数、响应结构
  - 接口描述（来自 `@Operation` 注解）
  - 模型定义（`User` 和 `Result` 的字段说明）

### 2.2 在 Swagger UI 中直接测试接口

- 点击任意接口（如 `POST /users`），点击 `Try it out`
- 在请求体编辑框中输入 JSON，点击 `Execute`
- 查看响应结果和 `curl` 命令示例
- **教学点**：展示 Swagger 如何提升前后端协作效率，并说明其自动生成原理。

------

## 3. 基础 CRUD 接口测试（使用 Postman 或浏览器）

### 3.1 创建用户（POST /users）

- **URL**：`http://localhost:8080/users`

- **方法**：POST

- **请求头**：`Content-Type: application/json`

- **请求体**（JSON）：

  json

  ```
  {
    "name": "张三",
    "age": 25
  }
  ```

  

- **预期响应**（200 OK）：

  json

  ```
  {
    "code": 200,
    "message": "成功",
    "data": {
      "id": 1,
      "name": "张三",
      "age": 25
    }
  }
  ```

  

- **观察**：返回的 `id` 由服务端自动生成（基于内存计数器），`data` 中包含新用户信息。

### 3.2 查询所有用户（GET /users）

- **URL**：`http://localhost:8080/users`

- **方法**：GET

- **预期响应**（200 OK）：

  ```
  {
    "code": 200,
    "message": "成功",
    "data": {
      "1": { "id": 1, "name": "张三", "age": 25 }
      // 可能有更多用户
    }
  }
  ```

  

- **观察**：返回的数据类型为 `Map<Long, User>`，演示了直接返回集合的格式。

### 3.3 根据ID查询用户（GET /users/{id}）

#### 3.3.1 查询存在的用户

- **URL**：`http://localhost:8080/users/1`

- **方法**：GET

- **预期响应**（200 OK）：

  ```
  {
    "code": 200,
    "message": "成功",
    "data": { "id": 1, "name": "张三", "age": 25 }
  }
  ```

  

#### 3.3.2 查询不存在的用户（触发业务异常）

- **URL**：`http://localhost:8080/users/999`

- **方法**：GET

- **预期响应**（200 OK，但 `code` 为 404）：

  ```
  {
    "code": 404,
    "message": "用户不存在",
    "data": null
  }
  ```

  

- **观察**：尽管HTTP状态码为200，但业务状态码为404。可讨论RESTful最佳实践：应该直接返回HTTP 404状态码。

### 3.4 更新用户（PUT /users/{id}）

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
    "code": 200,
    "message": "成功",
    "data": { "id": 1, "name": "张三（更新）", "age": 26 }
  }
  ```

  

- **观察**：更新后数据与请求体一致。

### 3.5 删除用户（DELETE /users/{id}）

- **URL**：`http://localhost:8080/users/1`

- **方法**：DELETE

- **预期响应**（200 OK）：

  ```
  {
    "code": 200,
    "message": "成功",
    "data": "删除成功"
  }
  ```

  

- **观察**：再次查询 ID=1 会返回“用户不存在”。

------

## 4. 全局异常处理演示

### 4.1 业务异常（用户不存在）

- **操作**：访问 `GET /users/999`（已在 3.3.2 演示）
- **观察**：控制台打印异常栈（因为 `GlobalExceptionHandler` 未抑制异常），响应为统一格式。

### 4.2 系统异常（模拟服务器内部错误）

为了演示 `Exception` 的捕获，可临时修改代码（或在测试中触发）。例如在 `UserController.getUserById` 中故意添加除零：

```
if (id == 2) {
    int a = 1 / 0;  // 制造异常
}
```



- **重启应用**，访问 `GET /users/2`

- **预期响应**（200 OK，但 code=500）：

  json

  ```
  {
    "code": 500,
    "message": "系统内部错误: / by zero",
    "data": null
  }
  ```

  

- **观察**：`message` 中包含异常信息（生产环境应隐藏细节，此处仅演示）。

------

## 5. 参数绑定进阶演示

### 5.1 @RequestParam 用法（搜索接口）

在 `UserController` 中添加一个搜索方法（若已存在则直接使用）：

```
@GetMapping("/search")
public Result<User> searchUser(@RequestParam(required = false) String name,
                               @RequestParam(defaultValue = "0") Integer age) {
    // 简单模拟：根据名称查找（实际应遍历 userMap）
    User found = userMap.values().stream()
            .filter(u -> name != null && name.equals(u.getName()))
            .findFirst().orElse(null);
    return Result.success(found);
}
```



- **URL**：`http://localhost:8080/users/search?name=张三&age=25`
- **方法**：GET
- **预期响应**：返回第一个匹配的用户（若有）。
- **演示点**：
  - `required=false` 表示参数可选。
  - `defaultValue="0"` 演示默认值。
  - 可尝试不传 `age`，观察其默认值。

### 5.2 @PathVariable 正则匹配（可选）

演示路径变量限制格式：

```
@GetMapping("/{id:[0-9]+}")
public Result<User> getUserByIdRegex(@PathVariable Long id) { ... }
```



- **访问** `/users/abc` → 由于不匹配 `[0-9]+`，Spring 会返回 404（由框架处理，不进入控制器）。

------

## 6. Swagger 注解效果验证

在 Swagger UI 页面，检查每个接口的文档是否符合预期：

- **@Operation**：应显示接口描述。
- **@Parameter**：路径参数应有说明。
- **@ApiResponse**：可看到 `404` 响应描述。
- **@Schema**：在 `User` 和 `Result` 模型中，字段应有说明。

例如 `GET /users/{id}` 的 `@Operation` 注解：

```
@Operation(summary = "根据ID查询用户")
@ApiResponse(responseCode = "404", description = "用户不存在")
```

在 Swagger 中会体现。

------

## 7. 课堂练习建议

- **练习1**：为 `User` 添加 `email` 字段，修改所有接口支持该字段，并在创建时校验邮箱格式（可抛自定义异常）。
- **练习2**：在全局异常处理器中增加对 `MethodArgumentNotValidException` 的处理（用于校验），返回400状态码。
- **练习3**：在 Swagger 中为每个响应添加 `@ApiResponse`，描述可能的错误码。

------

## 8. 常见问题排查

- **Swagger UI 无法访问**：检查依赖 `springdoc-openapi-starter-webmvc-ui` 是否存在，并确认无安全拦截。
- **全局异常不生效**：确保 `@RestControllerAdvice` 类被组件扫描（位于主类同包或子包）。
- **JSON 返回字段缺失**：检查实体类是否有 getter 方法。