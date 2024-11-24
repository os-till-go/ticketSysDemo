package com.example.ticket_system.service;

import com.example.ticket_system.model.Order;
import com.example.ticket_system.order.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ticket_system.model.Ticket;
import com.example.ticket_system.ticket.TicketRepository;
import com.example.ticket_system.model.User;
import com.example.ticket_system.user.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // 订单生成方法
    public Order createOrder(Long userId, Long ticketId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);

        if (userOptional.isEmpty() || ticketOptional.isEmpty()) {
            throw new RuntimeException("用户或票务不存在，无法生成订单");
        }

        User user = userOptional.get();
        Ticket ticket = ticketOptional.get();

        if (ticket.getSeatCount() <= 0) {
            throw new RuntimeException("票务库存不足，无法生成订单");
        }

        Order order = new Order();
        order.setUser(user);
        order.setTicket(ticket);
        order.setStatus("已创建");
        order.setPaymentStatus("未支付");

        // 减少票务库存
        ticket.setSeatCount(ticket.getSeatCount() - 1);
        ticketRepository.save(ticket);

        return orderRepository.save(order);
    }

    // 模拟支付方法，这里简单将支付状态更新为已支付
    public Order processPayment(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("订单不存在，无法进行支付操作");
        }

        Order order = orderOptional.get();
        order.setPaymentStatus("已支付");
        order.setStatus("已支付");
        return orderRepository.save(order);
    }

    // 根据订单ID查询订单信息
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("订单不存在"));
    }

    // 查询用户的所有订单
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    // 取消订单方法
    public void cancelOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("订单不存在，无法取消订单");
        }

        Order order = orderOptional.get();
        Ticket ticket = order.getTicket();

        // 释放库存，更新票务座位数等信息
        ticket.setSeatCount(ticket.getSeatCount() + 1);
        ticketRepository.save(ticket);

        // 更新订单状态为已取消
        order.setStatus("已取消");
        orderRepository.save(order);
    }
}