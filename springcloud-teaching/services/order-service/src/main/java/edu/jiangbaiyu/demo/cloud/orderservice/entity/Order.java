package edu.jiangbaiyu.demo.cloud.orderservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 * @author Robin
 * @date 2026/03/09
 */
@Data
@TableName("`order`")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String product;

    private BigDecimal amount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}