## 二、演示依赖范围

### 1. compile 范围（默认）

- **目的**：演示 compile 范围的依赖在编译、测试、运行时都可用。
- **操作**：
  1. 打开 `App.java`，其中使用了 `StringUtils`（来自 commons-lang3，compile 范围）。
  2. 执行 `mvn compile`，编译成功。
  3. 执行 `mvn test`，测试类 `ScopeTest.testCompileScope()` 通过。
- **说明**：compile 是最常用的范围，会被传递依赖。

### 2. provided 范围

- **目的**：演示 provided 依赖在编译和测试时存在，但运行时由容器提供（如 Servlet API）。

- **操作**：

  1. 取消 `App.java` 中注释的 `HttpServlet` 引用行（第14行附近），再次编译：

     ```
     mvn compile
     ```

     

     编译成功，说明 provided 依赖在编译期可见。

  2. 如果将此模块打包成 WAR 并部署到 Tomcat，则不会包含 servlet-api.jar，因为容器已提供。

  3. 执行测试 `ScopeTest.testProvidedScope()`，它断言 `HttpServlet` 类存在，测试通过（因为测试 classpath 包含 provided 依赖）。

- **说明**：provided 适用于编译时需要但运行时不打包的依赖（如 Servlet API、Lombok 等）。

### 3. runtime 范围

- **目的**：演示 runtime 依赖在编译时不需要，但运行时必需（如 JDBC 驱动）。
- **操作**：
  1. 尝试在 `App.java` 中直接导入 `org.h2.Driver`，会发现编译错误（因为 H2 是 runtime 范围，编译期不可见）。恢复原状。
  2. 运行测试 `ScopeTest.testRuntimeScope()`，它通过反射加载 H2 驱动类，测试通过。
  3. 执行 `mvn package` 打包，查看生成的 jar 包内容（不包含 h2 依赖，因为 runtime 范围不会打包进 jar，但如果在 Web 应用中会打包进 WEB-INF/lib）。
- **说明**：runtime 适用于 JDBC 驱动等，编译期只依赖接口，运行时需要具体实现。

### 4. test 范围

- **目的**：演示 test 依赖仅在测试代码中可见。
- **操作**：
  1. 在 `src/main/java` 下的某个类中尝试使用 JUnit（例如 `import org.junit.Test`），编译会失败。
  2. 在测试类中正常使用 JUnit，测试通过。
- **说明**：test 范围用于单元测试框架，不会被打包进最终制品。

### 5. system 范围（可选演示）

- **目的**：了解 system 范围用于引用本地 jar 包。
- **操作**：
  1. 在 `pom.xml` 中添加 system 依赖示例（需准备一个本地 jar 包），演示配置方式。
  2. 强调 system 范围通常不推荐，因为会破坏构建的可移植性。

------

## 三、演示依赖传递

### 1. 查看依赖树

- **操作**：

  ```
  mvn dependency:tree -pl maven-advanced
  ```

  

- **观察输出**：可以看到 `httpclient` 引入了 `httpcore`、`commons-logging` 等传递依赖。

- **说明**：Maven 会自动解析传递依赖，无需手动声明。

### 2. 在代码中使用传递依赖的类

- **操作**：
  1. 打开测试类 `TransitiveTest.java`。
  2. 其中使用了 `CloseableHttpClient`（直接依赖）和 `org.apache.http.HttpHost`（传递依赖）。
  3. 运行测试 `testTransitiveDependencies`，通过。
- **说明**：传递依赖让开发者只需关注直接依赖，简化依赖管理。

------

## 四、演示依赖冲突与调解

### 1. 观察冲突调解（路径最短优先）

- **背景**：`pom.xml` 中直接声明了 `commons-codec` 1.15，而 `httpclient` 原本传递依赖 `commons-codec` 1.11。

- **操作**（临时修改 pom 以观察冲突）：

  1. 注释掉 `httpclient` 的 `<exclusions>` 部分，保存。

  2. 执行命令查看依赖树：

     ```
     mvn dependency:tree -pl maven-advanced
     ```

     

  3. 观察输出：`commons-codec:commons-codec:jar:1.15:compile` 出现在直接依赖位置，而 `httpclient` 的传递依赖中不再有 commons-codec（因为 Maven 选择了路径更短的直接依赖 1.15）。

  4. **说明**：Maven 采用“最短路径优先”原则，直接依赖路径长度为 1，传递依赖路径长度为 2，因此选择 1.15。

- **恢复**：还原 pom 中的 exclusion。

### 2. 演示第一声明优先（当路径长度相同时）

- **场景**：如果有两个传递依赖引入相同 artifact 的不同版本，且路径长度相同，则先声明的生效。
- **操作**：可以临时添加另一个依赖（如某个库）也传递依赖 commons-codec 1.10，且路径长度相同，观察依赖树中生效的版本。

### 3. 演示排除传递依赖

- **目的**：强制移除某个传递依赖。
- **操作**：
  1. 打开 `pom.xml`，查看 `httpclient` 的 `<exclusions>` 部分。
  2. 执行 `mvn dependency:tree -pl maven-advanced`，观察到 `httpclient` 的依赖树中没有 `commons-codec`，因为它被排除了。
  3. 如果之前已经注释掉 exclusion，现在取消注释，再运行命令对比差异。
- **说明**：有时我们需要排除传递依赖中的某个库，例如避免版本冲突或不需要某些功能。通过 `<exclusions>` 可以精确控制。

### 4. 验证排除后的 classpath

- **操作**：运行测试类 `ConflictAndExclusionTest`，其中 `testCommonsCodecVersion` 使用了 commons-codec 1.15 的 API，测试通过，说明最终 classpath 中确实存在 1.15 版本（而不是被 httpclient 传递的 1.11 覆盖）。
- **注意**：如果排除无效，可能会因版本冲突导致方法不存在等错误，但这里 1.11 和 1.15 API 基本兼容，不易出错。可引入一个在两个版本中有差异的 API 来验证，但教学中可以说明原理。