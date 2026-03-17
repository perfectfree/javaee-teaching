#### **运行说明**

1. 运行 `App` 启动应用。
2. 在浏览器或 Postman 中访问以下 URL，观察控制台输出的切面日志：
   - 正常执行（触发 @Before, @After, @AfterReturning, @Around）：
     - `http://localhost:8085/aop/user/find?id=1`
     - `http://localhost:8085/aop/user/save?name=Jerry`
     - `http://localhost:8085/aop/product/find?id=20`
     - `http://localhost:8085/aop/product/delete?id=3`
   - 异常执行（触发 @AfterThrowing）：
     - `http://localhost:8085/aop/user/error?id=0` （id=0 会抛出异常，但控制器捕获后返回，注意控制台仍会打印异常通知）
3. 观察控制台输出，可以看到不同通知的执行顺序（@Before -> 方法执行 -> @AfterReturning/@AfterThrowing -> @After）。
4. 可以切换 `application.yml` 中的 `spring.aop.proxy-target-class` 值，观察代理方式的变化（如果设置为 false，UserService 使用 JDK 动态代理，ProductService 因为无接口仍会使用 CGLIB）。