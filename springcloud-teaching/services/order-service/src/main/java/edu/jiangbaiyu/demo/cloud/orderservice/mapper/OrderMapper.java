package edu.jiangbaiyu.demo.cloud.orderservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.jiangbaiyu.demo.cloud.orderservice.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Mapper接口
 * @author Robin
 * @date 2026/03/09
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}