package edu.jiangbaiyu.demo.cloud.userservice.dto;

import lombok.Data;

@Data
public class InternalUserDTO {
    private Long id;
    private String name;
    private String password;  // 用于认证
    private Integer age;
    private String email;
}