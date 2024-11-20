package com.example.ticket_system.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
