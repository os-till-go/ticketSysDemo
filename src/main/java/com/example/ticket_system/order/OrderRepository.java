package com.example.ticket_system.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.ticket_system.model.Order;
import com.example.ticket_system.model.User;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 根据用户查询其所有订单，由Spring Data JPA根据方法名自动生成查询语句
    List<Order> findByUser(User user);

    // 根据订单状态查询订单列表，同样由Spring Data JPA自动生成查询语句
    List<Order> findByStatus(String status);

    // 自定义查询，通过用户ID和订单状态查询订单列表
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status = :status")
    List<Order> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

    // 查询某个时间段内创建的订单
    @Query("SELECT o FROM Order o WHERE o.createTime BETWEEN :startTime AND :endTime")
    List<Order> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    // 查询某个用户在特定时间段内创建的订单
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.createTime BETWEEN :startTime AND :endTime")
    List<Order> findByUserIdAndCreateTimeBetween(@Param("userId") Long userId,
                                                 @Param("startTime") LocalDateTime startTime,
                                                 @Param("endTime") LocalDateTime endTime);

    // 查询已支付的订单，并按照支付时间降序排列
    @Query("SELECT o FROM Order o WHERE o.paymentStatus = '已支付' ORDER BY o.paymentTime DESC")
    List<Order> findPaidOrdersOrderedByPaymentTimeDesc();

    // 根据票务ID查询相关订单
    List<Order> findByTicketId(Long ticketId);
}