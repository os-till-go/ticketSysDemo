package com.example.ticket_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data

public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;  // 座位号，如A1、B2等
    private boolean selected;  // 是否已选
    @ManyToOne
    private Ticket ticket;  // 关联对应的票务
}
