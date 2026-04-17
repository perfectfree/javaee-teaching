package edu.jiangbaiyu.demo.cloud.authservice.feign;

import lombok.Data;

@Data
public class UserAuthDTO {
    private Long id;
    private String name;
    private String password;  // 仅用于认证，不暴露给前端
    private Integer age;
    private String email;
}