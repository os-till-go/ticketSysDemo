package com.example.ticket_system.service;

import com.example.ticket_system.model.Order;
import org.springframework.stereotype.Service;
import com.example.ticket_system.order.OrderRepository;
import com.example.ticket_system.ticket.TicketRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticsService {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;

    public StatisticsService(OrderRepository orderRepository, TicketRepository ticketRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
    }

    public BigDecimal calculateSalesBetween(LocalDateTime startTime, LocalDateTime endTime) {
        List<Order> orders = orderRepository.findByCreateTimeBetween(startTime, endTime);
        // ... 其他代码 ...
        return null;
    }

    public BigDecimal calculateTotalSales(LocalDateTime start, LocalDateTime end) {
        // 使用注入的orderRepository实例调用方法
        List<Order> orders = orderRepository.findByCreateTimeBetween(start, end);
        BigDecimal totalSales = BigDecimal.ZERO;
        for (Order order : orders) {
            totalSales = totalSales.add(order.getTicket().getPrice());
        }
        return totalSales;
    }
}