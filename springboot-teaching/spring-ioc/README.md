#### **运行说明**

- 运行 `App` 的 main 方法。
- 访问以下 URL 验证注入效果：
  - `http://localhost:8082/user/field` (使用字段注入，mock实现)
  - `http://localhost:8082/user/setter` (使用setter注入，真实实现)
  - `http://localhost:8082/user/constructor` (使用构造器注入，真实实现)
- 如果希望演示多个实现导致的歧义，可临时注释掉其中一个实现类的 `@Service` 注解，或按照Controller中被注释的部分尝试。