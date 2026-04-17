**模块作用**：提供微服务间的统一响应结构、全局异常处理、业务异常定义等基础组件。
**验证目标**：确保其他模块能正确引入并使用该模块的类。

#### 验证步骤

1. **编译检查**：在 `springcloud-teaching` 父目录执行 `mvn clean compile`，确认 `cloud-common` 模块编译成功。

2. **依赖验证**：检查 `department-service`、`user-service`、`order-service` 的 `pom.xml` 中是否已包含对 `cloud-common` 的依赖。

   ```
   <dependency>
       <groupId>edu.jiangbaiyu</groupId>
       <artifactId>cloud-common</artifactId>
       <version>1.0</version>
   </dependency>
   ```

   

3. **运行时验证**：启动任意一个微服务（如 `department-service`），访问一个会抛出 `BusinessException` 的接口，观察返回的 JSON 是否包含统一格式 `{code, message, data}`。

#### 预期结果

- 所有依赖 `cloud-common` 的服务都能正常编译启动。
- 当发生业务异常时，返回类似 `{"code":400,"message":"部门名称已存在","data":null}` 的响应。

#### 常见问题

- 若 `cloud-common` 未安装到本地仓库，需先在根目录执行 `mvn install`。
- 包扫描路径问题：确保各服务启动类扫描到了 `edu.jiangbaiyu.demo.cloud.common` 包（通过 `@SpringBootApplication` 默认扫描当前包及其子包，若启动类不在 `edu.jiangbaiyu.demo.cloud` 下，需手动添加 `@ComponentScan`）。