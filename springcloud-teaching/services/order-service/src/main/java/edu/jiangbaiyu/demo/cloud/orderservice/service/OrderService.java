package edu.jiangbaiyu.demo.cloud.orderservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.jiangbaiyu.demo.cloud.common.exception.BusinessException;
import edu.jiangbaiyu.demo.cloud.orderservice.dto.UserDTO;
import edu.jiangbaiyu.demo.cloud.orderservice.entity.Order;
import edu.jiangbaiyu.demo.cloud.orderservice.feign.UserFeignClient;
import edu.jiangbaiyu.demo.cloud.orderservice.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单服务层，包含业务逻辑与事务控制
 * @author Robin
 * @date 2026/03/09
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    @Autowired
    private UserFeignClient userFeignClient;


    /**
     * 创建订单，并校验用户是否存在（通过Feign调用用户服务）
     * @param order 订单信息
     * @return 保存后的订单
     */
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Order order) {
        // 1. 校验用户是否存在
        var userResp = userFeignClient.getUserById(order.getUserId());
        if (userResp.getCode() != 200 || userResp.getData() == null) {
            throw new BusinessException(400, "用户("+order.getUserId()+")不存在，无法创建订单");
        }
        UserDTO user = userResp.getData();
        // 2. 可以添加业务校验（例如用户年龄限制等）
        if (user.getAge() < 18) {
            throw new BusinessException(400, "未成年人不能下单");
        }
        // 3. 保存订单
        this.save(order);
        return order;
    }

    /**
     * 根据ID查询订单（只读事务）
     * @param id 订单ID
     * @return 订单信息
     */
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return this.getById(id);
    }
}