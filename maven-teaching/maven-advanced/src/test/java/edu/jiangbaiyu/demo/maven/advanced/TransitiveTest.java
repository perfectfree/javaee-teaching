package edu.jiangbaiyu.demo.maven.advanced;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 演示依赖传递：
 * 引入 httpclient 后，它会自动传递依赖 commons-logging 和 httpcore，
 * 测试代码中可以直接使用这些传递依赖的类。
 */
public class TransitiveTest {

    @Test
    public void testTransitiveDependencies() {
        // httpclient 本身
        CloseableHttpClient client = HttpClients.createDefault();
        assertNotNull(client);

        // 传递依赖：org.apache.commons.logging (commons-logging)
        assertNotNull(org.apache.commons.logging.LogFactory.class);

        // 传递依赖：org.apache.http.httpcore (httpcore)
        assertNotNull(org.apache.http.HttpHost.class);
    }
}