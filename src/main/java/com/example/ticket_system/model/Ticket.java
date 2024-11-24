package com.example.ticket_system.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String route; // 线路信息，例如火车车次线路、演出地点等
    private LocalDateTime departureTime; // 出发/演出等开始时间
    private BigDecimal price; // 票价
    private int seatCount; // 剩余座位数
    private boolean available; // 是否可用，用于标记票务是否处于可售卖状态，比如某些特殊场次可能临时不可售

    private LocalDateTime createTime; // 票务创建时间
    private LocalDateTime updateTime; // 票务信息更新时间

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>(); // 关联的座位信息列表，一个票务可以对应多个座位

    // 在实体持久化前（即保存到数据库前）调用此方法，用于初始化一些默认值，比如创建时间、初始可用状态等
    @PrePersist
    public void prePersist() {
        createTime = LocalDateTime.now();
        available = true; // 默认可用
    }

    // 在实体更新前（比如更新票务座位数、价格等操作前）调用此方法，用于更新更新时间字段
    @PreUpdate
    public void preUpdate() {
        updateTime = LocalDateTime.now();
    }
}
