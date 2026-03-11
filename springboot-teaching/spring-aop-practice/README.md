#### **运行说明**

1. 启动应用 `SpringAopPracticeApplication`。

2. 使用 Postman 或浏览器按以下顺序测试：

   - **设置角色为 ADMIN**：

     ```
     GET http://localhost:8086/practice/setRole?role=ADMIN
     ```

     

   - **测试创建用户（需要 ADMIN）**：

     ```
     POST http://localhost:8086/practice/user/create?name=Tom
     ```

     

     观察控制台输出：权限切面先执行，验证通过后日志切面执行。

   - **测试创建订单（需要 ADMIN）**：

     ```
     POST http://localhost:8086/practice/order/create?orderNo=ORDER001
     ```

     

   - **切换角色为 MANAGER**：

     ```
     GET http://localhost:8086/practice/setRole?role=MANAGER
     ```

     

   - **测试删除用户（需要 ADMIN/MANAGER）**：

     ```
     DELETE http://localhost:8086/practice/user/delete?id=10
     ```

     

     应该成功。

   - **测试创建订单（需要 ADMIN，但当前是 MANAGER）**：

     ```
     POST http://localhost:8086/practice/order/create?orderNo=ORDER002
     ```

     

     应抛出权限异常（返回500），控制台打印异常信息。

   - **测试内部调用失效**：

     ```
     GET http://localhost:8086/practice/user/internalCall?name=Jerry
     ```

     

     观察控制台：`internalCallDemo` 方法有日志输出，但其内部调用的 `createUser` 没有触发日志和权限切面（因为不是通过代理调用）。

   - **修复内部调用**（控制器直接调用代理）：

     ```
     GET http://localhost:8086/practice/user/internalCallFixed?name=Jerry
     ```

     

     由于控制器中的 `userService` 是代理对象，直接调用 `createUser` 会触发 AOP，日志和权限都生效。

3. 观察控制台输出，确认两个切面按 `@Order` 顺序执行（权限先，日志后）。