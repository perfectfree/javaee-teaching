package edu.jiangbaiyu.demo.springboot.config.controller;

import edu.jiangbaiyu.demo.springboot.config.properties.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置读取演示控制器
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    // 注入各个 Properties 类
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private AppListProperties listProperties;
    @Autowired
    private AppMapProperties mapProperties;
    @Autowired
    private AppNestedProperties nestedProperties;

    @Autowired
    private ErrorYamlProperties errorYamlProperties;

    @Autowired
    private ErrorProperties errorProperties;


    // 直接使用 @Value 注入
    @Value("${app.title:默认标题}")
    private String appTitle;  // 来自 application.properties

    @Value("${server.port}")
    private int serverPort;

    /**
     * 展示从 application.yml/properties 读取的基本配置
     */
    @GetMapping("/app")
    public Map<String, Object> getAppConfig() {
        Map<String, Object> result = new HashMap<>();
        result.put("appProperties", appProperties);
        result.put("listProperties", listProperties);
        result.put("mapProperties", mapProperties);
        result.put("nestedProperties", nestedProperties);
        result.put("appTitle", appTitle);
        result.put("serverPort", serverPort);
        return result;
    }

    /**
     * 展示从自定义 error-codes.properties 和 error-codes.yml 读取的配置
     */
    @GetMapping("/error-codes")
    public Map<String, Object> getErrorCodes() {
        Map<String, Object> result = new HashMap<>();
        // properties 内容
        result.put("properties_error404", errorProperties.getError404());
        result.put("properties_error500", errorProperties.getError500());
        result.put("properties_ignoredExceptions", errorProperties.getIgnoredExceptions());

        result.put("properties_errorMeessages", errorProperties.getMessages());
        // yml 内容（通过 Environment 获取）
        result.put("yml_error_codes", errorYamlProperties.getCodes());
        result.put("yml_retryableExceptions", errorYamlProperties.getRetryableExceptions());
        result.put("yml_severity", errorYamlProperties.getSeverity());
        result.put("yml_messages", errorYamlProperties.getMessages());
        return result;
    }

}