## 一、user-service 模块（Nacos 注册 + 配置中心）

**模块作用**：作为服务提供者，演示微服务注册到 Nacos 注册中心，并为后续 Feign 调用提供接口；同时展示配置中心动态刷新。

**演示目标**：
掌握 Nacos Server 的安装与启动，将 user-service 注册到 Nacos，并在控制台查看服务实例。

**关键操作**：

1. **安装 Nacos Server**

   - 从 GitHub 下载 Nacos 2.5.2 Server 包
   - 解压到本地（如 `D:\nacos-server-2.5.2`）
   - 进入 `bin` 目录
   - Windows：执行 `startup.cmd -m standalone`
   - Linux/Mac：执行 `./startup.sh -m standalone`

2. **验证 Nacos 启动**

   - 访问 `http://localhost:8848/nacos`
   - 默认账号密码均为 `nacos`
   - 查看控制台界面，确认无报错

3. **配置 user-service**

   - 在 `pom.xml` 中添加 Nacos Discovery 依赖：

   ```xml
   <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
   </dependency>
   ```

   - 创建数据库 `ms_user`，执行 `user-service/src/main/resources/schema.sql`。
   - 修改 `bootstrap.yml`，配置 Nacos 地址：

   ```yaml
   spring:
     application:
       name: user-service
     cloud:
       nacos:
         discovery:
           server-addr: 127.0.0.1:8848
   ```

   - 修改 `application.yml`，配置server.port=8821、数据库连接信息。

4. **启动 user-service**

   - 启动 `App`
   - 观察控制台日志，应包含：

   ```
   [main] c.a.c.n.registry.NacosServiceRegistry  : nacos registry, DEFAULT_GROUP user-service 192.168.x.x:8821 register finished
   ```

5. **验证注册**

   - 登录 Nacos 控制台
   - 进入“服务管理”→“服务列表”
   - 应看到 `user-service` 服务，实例数为 1，健康状态为 UP

6. **启动多实例**

   - 修改 `application.yml` 中的 `server.port` ，如 8822
   - 启动第二个 user-service 实例
   - 刷新 Nacos 控制台，实例列表显示两个实例（8821、8822）

7. **模拟实例下线**

   - 关闭其中一个实例
   - 观察 Nacos 控制台，几秒后该实例状态变为不健康并消失
   - 验证健康检查机制

**教学要点**：

- **注册中心作用**：服务消费者通过服务名发现提供者，解耦 IP 地址。
- **服务注册原理**：服务启动时向注册中心上报元数据（IP、端口、健康状态）
- **国产技术**：Nacos 由阿里巴巴开源，已成为 Apache 顶级项目，增强技术自信
- **健康检查**：注册中心定期发送心跳，剔除不健康实例
- **架构基石**：注册中心是微服务间通信的基础，体现“先有基础设施，再有上层建筑”的工程思想
- **配置中心优势**：配置集中管理、多环境隔离、动态刷新。

## 二、演示内容及预期结果

**服务注册**

- 打开 Nacos 控制台（http://localhost:8848/nacos），进入“服务管理”→“服务列表”。
- 应能看到 `user-service` 实例，且健康状态为“健康”。
- 可启动多个实例（修改 `server.port`），观察实例列表数量变化（多个实例时列表显示多个 IP:端口）。
- 实例下线后自动从列表中移除

**Feign 调用准备（配合 order-service 演示）**

- 确保 user-service 的 `/users/{id}` 等接口可被调用（本模块独立测试即可）。

**配置中心动态刷新**

1. 在 Nacos 控制台创建配置：

   - `Data ID`：`greating.yaml`

   - `Group`：`user-service`

   - 配置格式：`YAML`

   - 内容：

     ```yaml
     app:
       greeting: "欢迎使用用户服务（动态配置）"
     ```

2. 在 `UserController` 中添加：

   ```java
   @Value("${app.greeting:默认问候}")
   private String greeting;
   
   @GetMapping("/greeting")
   public R<String> greeting() {
       return R.success(greeting);
   }
   ```

   并给 Controller 类加上 `@RefreshScope`。

3. 启动 user-service，访问 `/users/greeting`，看到初始问候语。

4. 在 Nacos 控制台修改配置内容，点击“发布”，再次访问 `/users/greeting`，问候语实时更新，无需重启服务。

## 三、常见问题

- 注册不上：检查 Nacos 是否正常启动，`bootstrap.yml` 中 `spring.cloud.nacos.discovery.server-addr` 配置是否正确。
- 动态刷新不生效：检查是否添加了 `@RefreshScope`，且配置的 `dataId` 与 `spring.application.name` 和 `spring.profiles.active` 匹配。