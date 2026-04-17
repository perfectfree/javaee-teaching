package edu.jiangbaiyu.demo.cloud.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码加密工具类，统一使用 BCrypt 加密
 * @author Robin
 * @date 2026/03/21
 */
@Component
public class PasswordEncoderUtil {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 加密明文密码
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证明文密码是否与加密密码匹配
     * @param rawPassword 明文密码
     * @param encodedPassword 加密后的密码
     * @return true=匹配, false=不匹配
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}