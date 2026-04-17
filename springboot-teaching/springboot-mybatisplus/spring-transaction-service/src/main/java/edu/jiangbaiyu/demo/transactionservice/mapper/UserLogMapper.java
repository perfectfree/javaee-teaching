package edu.jiangbaiyu.demo.transactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.jiangbaiyu.demo.transactionservice.entity.UserLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户日志Mapper接口
 */
@Mapper
public interface UserLogMapper extends BaseMapper<UserLog> {
}