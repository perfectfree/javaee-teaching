#### **运行说明**

1. **准备数据库**：在MySQL中创建数据库 `javaee_teaching`（如果尚未创建），并确保用户名密码与 `application.yml` 中一致。
2. **启动应用**：运行 `App` 的 main 方法，观察控制台日志，schema.sql 会自动执行初始化。
3. **测试接口**（可使用 Postman 或浏览器）：
   - GET `http://localhost:8087/users` 获取所有用户
   - GET `http://localhost:8087/users/1` 获取ID为1的用户
   - POST `http://localhost:8087/users` 创建用户（JSON请求体，如 `{"name":"孙七","email":"sun@example.com","deptId":2,"status":1}`）
   - PUT `http://localhost:8087/users/1` 更新用户
   - DELETE `http://localhost:8087/users/1` 删除用户
   - GET `http://localhost:8087/users/search?name=张` 搜索用户
   - GET `http://localhost:8087/users/withDept` 关联查询用户及部门
   - POST `http://localhost:8087/users/transfer?userId=2&newDeptId=3` 转移部门（正常）
   - POST `http://localhost:8087/users/transfer?userId=2&newDeptId=3&simulateError=true` 模拟异常，事务回滚，部门应保持不变。
4. **观察事务回滚**：在模拟异常时，控制台会打印异常信息，数据库中的用户部门不应被修改。