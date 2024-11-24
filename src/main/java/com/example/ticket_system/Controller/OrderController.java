package com.example.ticket_system.Controller;

import com.example.ticket_system.model.Order;
import com.example.ticket_system.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ticket_system.model.User;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 创建订单接口
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestParam Long userId, @RequestParam Long ticketId) {
        Order order = orderService.createOrder(userId, ticketId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // 支付订单接口
    @PostMapping("/{orderId}/payment")
    public ResponseEntity<Order> processPayment(@PathVariable Long orderId) {
        Order order = orderService.processPayment(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // 根据订单ID查询订单接口
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // 查询用户所有订单接口
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Order> orders = orderService.getOrdersByUser(user);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 取消订单接口
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}