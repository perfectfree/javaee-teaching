package edu.jiangbaiyu.demo.cloud.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jiangbaiyu.demo.cloud.orderservice.App;
import edu.jiangbaiyu.demo.cloud.orderservice.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrder() throws Exception {
        Order order = new Order();
        order.setUserId(2L);
        order.setProduct("测试商品");
        order.setAmount(new BigDecimal("100.00"));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.userId").value(order.getUserId()));
    }

    @Test
    void testCreateOrder_UserNotFound() throws Exception {
        Order order = new Order();
        order.setUserId(999L);
        order.setProduct("商品");
        order.setAmount(new BigDecimal("50.00"));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("用户("+order.getUserId()+")不存在，无法创建订单"));
    }
}