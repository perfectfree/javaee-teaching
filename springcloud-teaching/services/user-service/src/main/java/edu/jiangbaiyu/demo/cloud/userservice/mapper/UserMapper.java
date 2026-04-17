package edu.jiangbaiyu.demo.cloud.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.jiangbaiyu.demo.cloud.userservice.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 * @author Robin
 * @date 2026/03/09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}