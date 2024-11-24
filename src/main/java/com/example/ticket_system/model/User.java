package com.example.ticket_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")  // 将 "user" 改为 "users"
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @Column
    private String phone; // 新增手机号字段，方便联系用户及可能的验证需求

    @Column
    private String address; // 新增地址字段，方便用户提供联系地址

    @Column
    private LocalDateTime registerTime; // 新增用户注册时间字段

    @Column
    private boolean enabled; // 用于标记用户账号是否可用，例如可用于封禁账号等情况

    // 在实体持久化前（即保存到数据库前）调用此方法，用于初始化一些默认值，比如注册时间、账号初始状态等
    @PrePersist
    public void prePersist() {
        registerTime = LocalDateTime.now();
        enabled = true; // 默认为账号可用状态
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    // 新增这些方法用于注册
    public void setRegistrationInfo(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = true;
    }
}
