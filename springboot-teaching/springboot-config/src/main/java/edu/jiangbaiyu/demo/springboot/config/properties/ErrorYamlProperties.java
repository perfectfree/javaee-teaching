package edu.jiangbaiyu.demo.springboot.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 读取 config/error-codes.yml 中的错误配置
 * 演示 YAML 格式中 Map、List 的绑定
 */
@Configuration
//@EnableConfigurationProperties(ErrorYamlProperties.class)
@ConfigurationProperties(prefix = "error-yml")
public class ErrorYamlProperties {

    // 映射 error.codes
    private Map<String, String> codes;

    // 映射 error.codeMessages
    private Map<String, String> messages;

    // 映射 error.retryable-exceptions
    private List<String> retryableExceptions;

    // 映射 error.severity
    private Map<String, String> severity;

    // getters / setters
    public Map<String, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<String, String> codes) {
        this.codes = codes;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public List<String> getRetryableExceptions() {
        return retryableExceptions;
    }

    public void setRetryableExceptions(List<String> retryableExceptions) {
        this.retryableExceptions = retryableExceptions;
    }

    public Map<String, String> getSeverity() {
        return severity;
    }

    public void setSeverity(Map<String, String> severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "ErrorYamlProperties{" +
                "codes=" + codes +
                ", messages=" + messages +
                ", retryableExceptions=" + retryableExceptions +
                ", severity=" + severity +
                '}';
    }
}