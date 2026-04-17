## 部门管理模块整合

**演示目标**：
完整实现部门管理的增删改查，展示三层架构（Controller-Service-Mapper）的整合，演示事务控制与统一异常处理。

**关键操作**：

1. **准备数据库**

   - 创建数据库 `ms_department`
   - 执行 `department-service/src/main/resources/schema.sql` 建表

2. **配置应用**

   - 修改 `application.yml` 中的数据库连接信息

3. **启动服务**

   - 启动 `App`
   - 观察控制台无报错，确认 MyBatis-Plus 初始化成功

4. **测试新增部门（正常场景）**

   - 使用 Postman 发送 `POST http://localhost:8810/departments`
   - 请求体：`{"name":"技术部"}`
   - 预期响应：

   ```json
   {
     "code": 200,
     "message": "success",
     "data": {
       "id": 1,
       "name": "技术部",
       "createTime": "2026-03-21T10:30:00",
       "updateTime": "2026-03-21T10:30:00"
     }
   }
   ```

   

   - 检查数据库，应新增一条记录

5. **测试唯一性校验（异常场景）**

   - 再次发送相同请求
   - 预期响应：

   ```json
   {
     "code": 400,
     "message": "部门名称已存在",
     "data": null
   }
   ```

   

   - 数据库无重复记录

6. **测试查询部门**

   - `GET http://localhost:8810/departments/1`
   - 预期返回部门信息
   - `GET http://localhost:8810/departments/999`
   - 预期返回 `data` 为 `null`

7. **演示事务回滚**

   - 在 `DepartmentService.addDepartment()` 方法中临时添加：

   ```json
   int i = 1 / 0; // 模拟异常
   ```

   

   - 重启服务，再次新增部门
   - 预期响应：`{"code":500,"message":"系统内部错误：/ by zero","data":null}`
   - 检查数据库，无新增记录
   - 控制台打印异常日志，证明事务回滚

**预期结果**：

- 部门新增成功，返回正确数据结构
- 唯一性校验生效，重复请求返回错误
- 模拟异常时事务回滚，数据库无脏数据

**教学要点**：

- **事务边界**：`@Transactional` 放在 Service 层方法上，确保业务操作原子性
- **统一响应**：所有接口返回 `R<T>`，便于前端处理
- **全局异常**：`GlobalExceptionHandler` 统一捕获业务异常并返回规范 JSON
- **MyBatis-Plus 自动填充**：`createTime` 和 `updateTime` 自动维护
- **工匠精神**：注重代码规范、命名、异常处理