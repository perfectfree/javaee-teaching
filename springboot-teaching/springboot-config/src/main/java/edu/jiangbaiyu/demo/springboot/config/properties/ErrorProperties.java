package edu.jiangbaiyu.demo.springboot.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * 读取 config/error-codes.properties 中的错误配置
 * - 使用 @Value 读取纯数字键（error.404, error.500），因为 @ConfigurationProperties 无法直接绑定数字开头的键
 * - 使用 @ConfigurationProperties 读取 Map 和数组类型
 */
@Configuration
@PropertySource(value = "classpath:config/error-codes.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "error")
public class ErrorProperties {

    // ========== 纯数字键单独注入（@Value 方式） ==========
    @Value("${error.404}")
    private String error404;

    @Value("${error.500}")
    private String error500;

    // ========== Map 绑定：将所有 error.messages.* 注入为 Map ==========
    private Map<String, String> messages;

    // ========== 数组/List 绑定：error.ignored.exceptions 自动拆分 ==========
    @Value("${error.ignored.exceptions}")
    private String[] ignoredExceptions;

    // ========== getters / setters ==========
    public String getError404() {
        return error404;
    }

    public void setError404(String error404) {
        this.error404 = error404;
    }

    public String getError500() {
        return error500;
    }

    public void setError500(String error500) {
        this.error500 = error500;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public String[] getIgnoredExceptions() {
        return ignoredExceptions;
    }

    public void setIgnoredExceptions(String[] ignoredExceptions) {
        this.ignoredExceptions = ignoredExceptions;
    }

    @Override
    public String toString() {
        return "ErrorProperties{" +
                "error404='" + error404 + '\'' +
                ", error500='" + error500 + '\'' +
                ", messages=" + messages +
                ", ignoredExceptions=" + java.util.Arrays.toString(ignoredExceptions) +
                '}';
    }
}