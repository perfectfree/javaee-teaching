package edu.jiangbaiyu.demo.cloud.orderservice.feign;

import edu.jiangbaiyu.demo.cloud.common.result.R;
import edu.jiangbaiyu.demo.cloud.orderservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", fallback = UserFeignFallback.class)
public interface UserFeignClient {

    @GetMapping("/users/{id}")
    R<UserDTO> getUserById(@PathVariable("id") Long id);
}