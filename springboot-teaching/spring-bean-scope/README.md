#### **运行说明**

1. 运行 `App` 的 main 方法。
2. 在浏览器或 Postman 中访问：
   - `http://localhost:8083/scope/singleton` 多次访问，观察 count 递增，hashCode 不变。
   - `http://localhost:8083/scope/prototype` 多次访问，每次 hashCode 不同，count 始终为 1（因为是新实例）。
   - `http://localhost:8083/scope/prototype2` 同样演示原型作用域，使用 `@Lookup` 方法。
   - `http://localhost:8083/scope/lifecycle` 触发业务方法，注意控制台在应用启动和关闭时的生命周期回调输出。
3. 应用关闭时，观察控制台 `@PreDestroy` 和 `DisposableBean` 的输出。