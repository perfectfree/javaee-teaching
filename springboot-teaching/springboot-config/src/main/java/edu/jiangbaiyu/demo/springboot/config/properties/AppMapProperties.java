package edu.jiangbaiyu.demo.springboot.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 绑定 map 示例
 */
@Component
@ConfigurationProperties(prefix = "map")
public class AppMapProperties {
    private Map<String, String> settings;

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "MapProperties{settings=" + settings + '}';
    }
}
