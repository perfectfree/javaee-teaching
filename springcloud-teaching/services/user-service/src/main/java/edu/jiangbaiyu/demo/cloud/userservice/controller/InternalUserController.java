package edu.jiangbaiyu.demo.cloud.userservice.controller;

import edu.jiangbaiyu.demo.cloud.common.result.R;
import edu.jiangbaiyu.demo.cloud.userservice.dto.InternalUserDTO;
import edu.jiangbaiyu.demo.cloud.userservice.entity.User;
import edu.jiangbaiyu.demo.cloud.userservice.service.UserService;
import edu.jiangbaiyu.demo.cloud.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内部接口，仅供其他微服务调用（如 auth-service）
 */
@RestController
@RequestMapping("/internal/users")
public class InternalUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/username/{username}")
    public R<InternalUserDTO> getUserByUsername(@PathVariable("username") String username) {
        // 根据用户名查询用户（假设用户名是 name 字段）
        User user = userService.lambdaQuery().eq(User::getName, username).one();
        if (user == null) {
            return R.error(404, "用户不存在");
        }
        InternalUserDTO dto = new InternalUserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword()); // 仅用于认证，注意密码安全
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        return R.success(dto);
    }
}