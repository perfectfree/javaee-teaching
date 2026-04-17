#### **运行说明**

- 访问 `http://localhost:8080/h2-console`，JDBC URL 使用 `jdbc:h2:mem:advanceddb`，可查看数据。
- 使用 Postman 测试 API：
    - POST `/users` 添加用户。
    - GET `/users/search?name=张` 条件查询。
    - GET `/users/page?current=1&size=3` 分页查询。
    - DELETE `/users/1` 逻辑删除，之后查询该 ID 将返回空（自动过滤）。
    - GET `/users/all-include-deleted` 查看包括逻辑删除的所有数据（演示自定义查询，实际测试可能需要调整）。
- 观察控制台打印的 SQL，验证分页、逻辑删除、自动填充是否生效。