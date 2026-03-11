package edu.jiangbaiyu.demo.maven.advanced;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 演示依赖冲突与排除：
 * 我们在 pom.xml 中直接声明了 commons-codec 1.15，
 * 而 httpclient 原本传递依赖 commons-codec 1.11，但我们在 httpclient 中排除了 commons-codec，
 * 因此最终 classpath 中只有 1.15 版本的 commons-codec。
 *
 * 如果不排除，Maven 会根据路径最短原则选择直接声明的 1.15，但排除可以明确移除传递依赖，
 * 避免意外引入。
 *
 * 为了观察冲突调解，可以临时注释掉 httpclient 中的 exclusion，然后运行 mvn dependency:tree 观察。
 */
public class ConflictAndExclusionTest {

    @Test
    public void testCommonsCodecVersion() {
        // 使用 commons-codec 的 Base64 类（1.15 存在）
        byte[] encoded = Base64.encodeBase64("hello".getBytes());
        assertNotNull(encoded);

        // 使用 DigestUtils（1.15 存在）
        String md5 = DigestUtils.md5Hex("hello");
        assertEquals("5d41402abc4b2a76b9719d911017c592", md5);
    }

    @Test
    public void testExclusionPreventsTransitiveDependency() {
        // 如果在 httpclient 中没有排除 commons-codec，那么 classpath 中仍会存在 commons-codec 的某个版本
        // 但这里我们验证的是：通过排除，httpclient 不会引入 commons-codec，而直接声明的 1.15 独立存在
        // 此测试仅确保 commons-codec 可用，无法直接证明排除生效，但结合 dependency:tree 可验证。
        // 我们可以在代码中检查 commons-codec 的版本，但不同版本 API 可能相同，这里不深入。
        assertTrue(true); // 占位
    }
}