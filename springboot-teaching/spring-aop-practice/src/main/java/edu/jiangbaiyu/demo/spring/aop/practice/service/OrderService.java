package edu.jiangbaiyu.demo.spring.aop.practice.service;

import edu.jiangbaiyu.demo.spring.aop.practice.annotation.LogExecution;
import edu.jiangbaiyu.demo.spring.aop.practice.annotation.RequireRole;
import org.springframework.stereotype.Service;

/**
 * 订单服务，演示不同角色的权限控制
 * @author Robin
 */
@Service
public class OrderService {

    @LogExecution("查询订单")
    public String findOrderById(Integer id) {
        System.out.println("【OrderService】执行 findOrderById，id=" + id);
        return "订单信息：Order-" + id;
    }

    @LogExecution("创建订单")
    @RequireRole("ADMIN")
    public String createOrder(String orderNo) {
        System.out.println("【OrderService】执行 createOrder，orderNo=" + orderNo);
        return "订单创建成功：" + orderNo;
    }

    @RequireRole("ADMIN")
    public String cancelOrder(Integer id) {
        System.out.println("【OrderService】执行 cancelOrder，id=" + id);
        return "订单取消成功：" + id;
    }
}