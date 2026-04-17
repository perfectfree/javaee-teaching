package edu.jiangbaiyu.demo.cloud.orderservice.service;

import edu.jiangbaiyu.demo.cloud.common.exception.BusinessException;
import edu.jiangbaiyu.demo.cloud.orderservice.App;
import edu.jiangbaiyu.demo.cloud.orderservice.entity.Order;
import edu.jiangbaiyu.demo.cloud.orderservice.feign.UserFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = App.class)
class OrderServiceTest {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private OrderService orderService;

    @Test
    void testCreateOrder_UserExists() {
        Order order = new Order();
        order.setUserId(2L);
        order.setProduct("笔记本电脑");
        order.setAmount(new BigDecimal("5999.00"));

        Order result = orderService.createOrder(order);
        assertNotNull(result);
        assertEquals(2L, result.getUserId());
        assertEquals("笔记本电脑", result.getProduct());
    }

    @Test
    void testCreateOrder_UserNotExists() {

        Order order = new Order();
        order.setUserId(1L);
        order.setProduct("手机");
        order.setAmount(new BigDecimal("3999.00"));

        assertThrows(BusinessException.class, () -> orderService.createOrder(order));
    }

    @Test
    void testCreateOrder_UserAgeTooYoung() {
        Order order = new Order();
        order.setUserId(22L);
        order.setProduct("游戏机");
        order.setAmount(new BigDecimal("2999.00"));

        assertThrows(BusinessException.class, () -> orderService.createOrder(order));
    }
}