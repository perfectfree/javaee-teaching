#### **运行说明**
- 运行 `App` 的 main 方法。
- 使用Postman测试登录：`POST http://localhost:8089/api/login`，参数 `username` 和 `password`（如 user/123456）。应返回JSON格式成功信息。
- 登录成功后，访问需要权限的接口：`GET http://localhost:8089/api/user/info`（需携带Session或认证头，默认表单登录会建立会话）。测试不同用户。