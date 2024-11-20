package com.example.ticket_system.ticket;

import com.example.ticket_system.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ticket_system.order.Order;
import com.example.ticket_system.ticket.Ticket;
import com.example.ticket_system.user.User;
import com.example.ticket_system.user.UserRepository;
import com.example.ticket_system.order.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final UserRepository userRepository;

    public TicketController(TicketService ticketService, UserRepository userRepository) {
        this.ticketService = ticketService;
        this.userRepository = userRepository;
    }

    // 查询所有可用票务信息接口
    @GetMapping("/available")
    public ResponseEntity<List<Ticket>> getAvailableTickets() {
        List<Ticket> tickets = ticketService.getAvailableTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    // 根据票务ID查询票务详细信息接口
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    // 根据线路模糊查询可用票务信息接口
    @GetMapping("/search/route")
    public ResponseEntity<List<Ticket>> searchTicketsByRoute(@RequestParam String routeKeyword) {
        List<Ticket> tickets = ticketService.searchTicketsByRoute(routeKeyword);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    // 根据出发时间范围查询可用票务信息接口
    @GetMapping("/search/departure-time")
    public ResponseEntity<List<Ticket>> searchTicketsByDepartureTime(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        return new ResponseEntity<>(ticketService.searchTicketsByDepartureTime(startTime, endTime), HttpStatus.OK);
    }

    // 购票接口，接收用户ID和票务ID，进行购票操作
    @PostMapping("/purchase")
    public ResponseEntity<Order> purchaseTicket(
            @RequestParam Long userId,
            @RequestParam Long ticketId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("用户不存在"));
        Ticket ticket = ticketService.getTicketById(ticketId);
        Order order = ticketService.purchaseTicket(user, ticket);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // 退票接口，接收订单ID，进行退票操作
    @DeleteMapping("/refund")
    public ResponseEntity<Void> refundTicket(@RequestParam Long orderId) {
        ticketService.refundTicket(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 更新票务信息接口，接收包含更新后信息的票务对象，进行票务信息更新操作
    @PutMapping("/update")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket updatedTicket) {
        Ticket ticket = ticketService.updateTicket(updatedTicket);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }
}