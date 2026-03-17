### 验证与运行

- 启动`App`
- 使用Postman测试各个接口：
  - POST `/users/with-log`：正常插入用户和日志。
  - POST `/users/with-log-error`：用户插入后日志抛出异常，用户表应无数据（回滚）。
  - POST `/users/with-independent-log`：用户插入成功，日志插入失败（由于REQUIRES_NEW且被捕获），用户仍存在，日志不存在。
  - PUT `/users/no-rollback`：更新用户并抛出`IllegalArgumentException`，但事务提交（观察数据库是否更新）。
- 观察控制台打印的SQL和事务日志。