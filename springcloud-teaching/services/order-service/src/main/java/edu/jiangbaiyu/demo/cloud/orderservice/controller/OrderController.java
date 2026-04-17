package edu.jiangbaiyu.demo.cloud.orderservice.controller;

import edu.jiangbaiyu.demo.cloud.common.result.R;
import edu.jiangbaiyu.demo.cloud.orderservice.entity.Order;
import edu.jiangbaiyu.demo.cloud.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器，提供RESTful API
 * @author Robin
 * @date 2026/03/09
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * POST /orders
     */
    @PostMapping
    public R<Order> create(@Valid @RequestBody Order order) {
        return R.success(orderService.createOrder(order));
    }

    /**
     * 根据ID查询订单
     * GET /orders/{id}
     */
    @GetMapping("/{id}")
    public R<Order> getById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return R.error(404, "订单不存在");
        }
        return R.success(order);
    }
}