package com.example.ticket_system.service;

import com.example.ticket_system.module.ticket.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ticket_system.model.Order;
import com.example.ticket_system.module.order.OrderRepository;
import com.example.ticket_system.model.Ticket;
import com.example.ticket_system.model.User;
import com.example.ticket_system.module.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // 查询所有可用票务信息的方法
    public List<Ticket> getAvailableTickets() {
        return ticketRepository.findByAvailableTrue();
    }

    // 根据票务ID查询票务详细信息的方法
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("票务不存在"));
    }

    // 购票方法，接收用户和票务信息，进行购票相关逻辑处理
    public Order purchaseTicket(User user, Ticket ticket) {
        if (!ticket.isAvailable()) {
            throw new RuntimeException("该票务当前不可用，无法购买");
        }

        if (ticket.getSeatCount() <= 0) {
            throw new RuntimeException("票务库存不足，无法购买");
        }

        Order order = new Order();
        order.setUser(user);
        order.setTicket(ticket);
        order.setStatus("已创建");
        order.setPaymentStatus("未支付");

        // 减少票务库存
        ticket.setSeatCount(ticket.getSeatCount() - 1);
        if (ticket.getSeatCount() == 0) {
            ticket.setAvailable(false); // 如果库存为0，设置票务不可用
        }

        ticketRepository.save(ticket);
        return orderRepository.save(order);
    }

    // 退票方法，接收订单ID，进行退票相关逻辑处理
    public void refundTicket(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("订单不存在，无法退票");
        }

        Order order = orderOptional.get();
        Ticket ticket = order.getTicket();

        // 释放库存，增加票务座位数
        ticket.setSeatCount(ticket.getSeatCount() + 1);
        ticket.setAvailable(true); // 设置票务为可用状态

        ticketRepository.save(ticket);

        // 更新订单状态为已取消
        order.setStatus("已取消");
        orderRepository.save(order);
    }

    // 更新票务信息的方法，例如修改票价、出发时间等
    public Ticket updateTicket(Ticket updatedTicket) {
        Ticket existingTicket = getTicketById(updatedTicket.getId());
        // 根据业务需求，选择性地更新部分属性，避免全量覆盖导致原有重要信息丢失
        existingTicket.setPrice(updatedTicket.getPrice());
        existingTicket.setDepartureTime(updatedTicket.getDepartureTime());
        existingTicket.setAvailable(updatedTicket.isAvailable());
        return ticketRepository.save(existingTicket);
    }

    public List<Ticket> searchTicketsByRoute(String routeKeyword) {
        return ticketRepository.findAvailableRouteLike(routeKeyword);
    }

    // 添加新方法
    public List<Ticket> searchTicketsByDepartureTime(LocalDateTime startTime, LocalDateTime endTime) {
        return ticketRepository.findAvailableByDepartureTimeBetween(startTime, endTime);
    }
}