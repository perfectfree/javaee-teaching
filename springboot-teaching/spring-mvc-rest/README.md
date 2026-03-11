#### **测试说明**

使用 Postman 或浏览器测试以下接口：

| 方法   | URL                                           | 描述              | 示例请求体                                                 |
| :----- | :-------------------------------------------- | :---------------- | :--------------------------------------------------------- |
| GET    | `http://localhost:8084/users`                 | 获取所有用户      | 无                                                         |
| GET    | `http://localhost:8084/users/1`               | 获取ID为1的用户   | 无                                                         |
| POST   | `http://localhost:8084/users`                 | 创建用户          | `{"name":"赵六","email":"zhao@example.com","status":1}`    |
| PUT    | `http://localhost:8084/users/1`               | 更新用户          | `{"name":"张三丰","email":"zhang@example.com","status":1}` |
| DELETE | `http://localhost:8084/users/1`               | 删除用户          | 无                                                         |
| GET    | `http://localhost:8084/users/filter?status=1` | 查询状态为1的用户 | 无                                                         |

- 创建成功应返回201状态码，并在响应体中包含生成的ID。
- 查询不存在的资源应返回404。
- 删除成功应返回204。