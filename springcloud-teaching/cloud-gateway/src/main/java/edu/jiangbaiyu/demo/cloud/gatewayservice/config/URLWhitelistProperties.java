package edu.jiangbaiyu.demo.cloud.gatewayservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "custom-gateway.whitelist")
public class URLWhitelistProperties {
    private List<String> paths = new ArrayList<>();

    public List<String> getPaths() { return paths; }
    public void setPaths(List<String> paths) { this.paths = paths; }
}
