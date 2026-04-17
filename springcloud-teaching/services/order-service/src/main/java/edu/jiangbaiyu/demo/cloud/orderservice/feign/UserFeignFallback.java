package edu.jiangbaiyu.demo.cloud.orderservice.feign;

import edu.jiangbaiyu.demo.cloud.common.result.R;
import edu.jiangbaiyu.demo.cloud.orderservice.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserFeignFallback implements UserFeignClient {
    @Override
    public R<UserDTO> getUserById(Long id) {
        // 降级逻辑：返回一个默认用户或错误提示
        UserDTO fallbackUser = new UserDTO();
        fallbackUser.setId(id);
        fallbackUser.setName("降级用户");
        fallbackUser.setAge(0);
        fallbackUser.setEmail("fallback@example.com");
        return R.success(fallbackUser);
    }
}