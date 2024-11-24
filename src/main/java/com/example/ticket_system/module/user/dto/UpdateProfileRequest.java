package com.example.ticket_system.module.user.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String username;
    private String email;
    private String phone;
    private String address;
}