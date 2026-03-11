#### **运行说明**

- 启动应用，访问 `http://localhost:8088/hello` 无需登录。
- 访问 `http://localhost:8088/user/info` 会被重定向到登录页。
- 使用配置的内存用户 `user/123456` 或 `admin/admin123` 登录。
- 登录后访问 `/admin/dashboard`，`admin` 用户可以访问，`user` 用户会返回403。