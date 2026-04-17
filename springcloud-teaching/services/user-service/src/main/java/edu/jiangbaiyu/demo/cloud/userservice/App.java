package edu.jiangbaiyu.demo.cloud.userservice;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("edu.jiangbaiyu.demo.cloud.common;edu.jiangbaiyu.demo.cloud.userservice")
@MapperScan("edu.jiangbaiyu.demo.cloud.userservice.mapper")
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
