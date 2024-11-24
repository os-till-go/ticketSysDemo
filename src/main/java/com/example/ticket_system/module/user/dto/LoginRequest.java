package com.example.ticket_system.module.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
