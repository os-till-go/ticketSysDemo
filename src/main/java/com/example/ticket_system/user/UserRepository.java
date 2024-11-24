package com.example.ticket_system.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.ticket_system.model.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // 根据用户名查询用户，由Spring Data JPA根据方法名自动生成查询语句
    User findByUsername(String username);

    // 根据邮箱查询用户，同样由Spring Data JPA自动生成查询语句
    User findByEmail(String email);

    // 根据手机号查询用户，用于可能的验证、查找特定用户等场景
    User findByPhoneNumber(String phoneNumber);

    // 查询所有启用状态（账号可用）的用户

    List<User> findByEnabledTrue();

    // 自定义查询，通过用户名或邮箱模糊查询用户，方便搜索功能
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<User> findByKeyword(@Param("keyword") String keyword);
}