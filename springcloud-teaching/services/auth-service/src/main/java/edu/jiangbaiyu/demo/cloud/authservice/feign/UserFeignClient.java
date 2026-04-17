package edu.jiangbaiyu.demo.cloud.authservice.feign;

import edu.jiangbaiyu.demo.cloud.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    /**
     * 调用 user-service 的内部接口，根据用户名获取用户信息（含密码）
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/internal/users/username/{username}")
    R<UserAuthDTO> getUserByUsername(@PathVariable("username") String username);
}