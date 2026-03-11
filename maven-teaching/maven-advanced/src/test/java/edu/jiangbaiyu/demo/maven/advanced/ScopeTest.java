package edu.jiangbaiyu.demo.maven.advanced;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.sql.Driver;
import java.sql.DriverManager;

import static org.junit.Assert.*;

/**
 * 演示依赖范围：
 * - compile: 在测试代码中可用
 * - provided: 在测试代码中可用（因为测试运行时 classpath 包含 provided 依赖）
 * - runtime: 在测试代码中不可直接编译引用，但可以通过反射或 DriverManager 加载
 * - test: 仅测试代码可见
 */
public class ScopeTest {

    @Test
    public void testCompileScope() {
        // commons-lang3 是 compile 范围，测试中可用
        String reversed = StringUtils.reverse("abc");
        assertEquals("cba", reversed);
    }

    @Test
    public void testProvidedScope() {
        // javax.servlet-api 是 provided 范围，但在测试环境中，Maven 会将 provided 依赖加入测试 classpath
        // 所以这里可以正常引用
        assertNotNull(javax.servlet.http.HttpServlet.class);
    }

    @Test
    public void testRuntimeScope() {
        // H2 驱动是 runtime 范围，编译期不能直接引用 com.h2database 中的类（除非在测试代码中引用，但 Maven 会将 runtime 依赖加入测试 classpath？）
        // 实际上，Maven 在测试阶段会将 runtime 范围的依赖也加入 classpath，所以测试代码中可以直接引用 H2 的类。
        // 这里我们验证能否加载驱动类（反射方式更安全）
        try {
            Class<?> driverClass = Class.forName("org.h2.Driver");
            assertNotNull(driverClass);
        } catch (ClassNotFoundException e) {
            fail("H2 Driver should be available in test classpath");
        }

        // 也可以通过 DriverManager 检查驱动是否注册
        try {
            Driver driver = DriverManager.getDriver("jdbc:h2:mem:test");
            assertNotNull(driver);
        } catch (Exception e) {
            // 可能没有注册，但驱动类存在即可
        }
    }

    // test 范围的依赖 junit 自然可用（因为我们在写测试类）
}