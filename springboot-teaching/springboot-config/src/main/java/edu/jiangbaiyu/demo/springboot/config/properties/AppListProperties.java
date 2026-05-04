package edu.jiangbaiyu.demo.springboot.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 绑定 list 示例
 */
@Component
@ConfigurationProperties(prefix = "list")
public class AppListProperties {
    private List<String> colors;

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "ListProperties{colors=" + colors + '}';
    }
}
