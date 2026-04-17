package edu.jiangbaiyu.demo.cloud.authservice.service;

import edu.jiangbaiyu.demo.cloud.authservice.feign.UserAuthDTO;
import edu.jiangbaiyu.demo.cloud.authservice.feign.UserFeignClient;
import edu.jiangbaiyu.demo.cloud.common.exception.BusinessException;
import edu.jiangbaiyu.demo.cloud.common.utils.JwtUtil;
import edu.jiangbaiyu.demo.cloud.common.utils.PasswordEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;
    /**
     * 登录验证，通过 Feign 调用 user-service 获取用户信息，生成 JWT
     */
    public String login(String username, String password) {
        // 调用 user-service 内部接口获取用户信息（包含加密密码）
        var result = userFeignClient.getUserByUsername(username);
        if (result.getCode() != 200 || result.getData() == null) {
            throw new BusinessException(401, "用户名不存在");
        }
        UserAuthDTO user = result.getData();

        // 使用公共工具类验证密码
        if (!passwordEncoderUtil.matches(password, user.getPassword())) {
            throw new BusinessException(401, "密码错误");
        }

        // 生成 JWT
        return jwtUtil.generateToken(user.getId(), user.getName());
    }
}