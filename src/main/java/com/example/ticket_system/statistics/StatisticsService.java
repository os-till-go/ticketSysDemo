package com.example.ticket_system.statistics;

import com.example.ticket_system.order.Order;
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

    public BigDecimal calculateTotalSales(LocalDateTime start, LocalDateTime end) {
        List<Order> orders = OrderRepository.findByPurchaseTimeBetween(start, end);  // 假设订单有购买时间字段
        BigDecimal totalSales = BigDecimal.ZERO;
        for (Order order : orders) {
            totalSales = totalSales.add(order.getTicket().getPrice());
        }
        return totalSales;
    }
}
