package com.example.ticket_system.ticket;

import com.example.ticket_system.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.ticket_system.model.Ticket;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // 根据是否可用查询票务列表，由Spring Data JPA根据方法名自动生成查询语句
    List<Ticket> findByAvailableTrue();

    // 根据线路信息模糊查询可用票务，方便用户查找特定线路的票务
    // 修正方法名
    @Query("SELECT t FROM Ticket t WHERE t.available = true AND t.route LIKE %:routeKeyword%")
    List<Ticket> findAvailableRouteLike(@Param("routeKeyword") String routeKeyword);
    // 根据出发时间范围查询可用票务，用于筛选特定时间段内的票务
    @Query("SELECT t FROM Ticket t WHERE t.available = true AND t.departureTime BETWEEN :startTime AND :endTime")
    List<Ticket> findAvailableByDepartureTimeBetween(@Param("startTime") LocalDateTime startTime,
                                                            @Param("endTime") LocalDateTime endTime);

    // 查询某张票务关联的所有座位信息，通过票务ID进行关联查询
    @Query("SELECT s FROM Seat s WHERE s.ticket.id = :ticketId")
    List<Seat> findSeatsByTicketId(@Param("ticketId") Long ticketId);
}