package com.example.ticket_system.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")  // 修改表名，因为"order"是SQL关键字
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user; // 关联用户，表示该订单属于哪个用户

    @ManyToOne
    private Ticket ticket; // 关联票务，表明该订单对应的是哪张票务

    private String status; // 订单状态，如"已创建"、"已支付"、"已取消"、"已退款"等
    private String paymentStatus; // 支付状态，如"已支付"、"未支付"、"部分支付"等

    //private LocalDateTime createTime; // 订单创建时间
    private LocalDateTime paymentTime; // 订单支付时间
    private LocalDateTime cancelTime; // 订单取消时间（若订单被取消）
    private LocalDateTime refundTime; // 订单退款时间（若订单被退款）

    private BigDecimal totalAmount; // 订单总金额，可能包含票价、手续费等（若有）

    // 用于记录订单是否已经发送通知，例如购票成功、退款成功等通知是否已发送给用户
    private boolean notificationSent;

    @Column(name = "create_time")
    private LocalDateTime createTime;  // 假设这是我们要查询的时间字段

    // 在实体持久化前（即保存到数据库前）调用此方法，用于初始化一些默认值，比如创建时间等
    @PrePersist
    public void prePersist() {
        createTime = LocalDateTime.now();
    }

    // 在实体更新前（比如更新订单状态等操作前）调用此方法，可用于更新一些时间相关字段等
    @PreUpdate
    public void preUpdate() {
        if ("已取消".equals(status) && cancelTime == null) {
            cancelTime = LocalDateTime.now();
        }
        if ("已退款".equals(status) && refundTime == null) {
            refundTime = LocalDateTime.now();
        }
        if ("已支付".equals(paymentStatus) && paymentTime == null) {
            paymentTime = LocalDateTime.now();
        }
    }
}
