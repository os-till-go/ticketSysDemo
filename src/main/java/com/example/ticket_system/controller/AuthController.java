package com.example.ticket_system.controller;

import com.example.ticket_system.model.User;
import com.example.ticket_system.module.user.dto.LoginRequest;
import com.example.ticket_system.module.user.dto.RegisterRequest;
import com.example.ticket_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("token", token);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new HashMap<String, String>() {{
                        put("message", "登录失败：" + e.getMessage());
                    }});
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request.getUsername(),
                    request.getPassword(),
                    request.getEmail());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new HashMap<String, String>() {{
                        put("message", "注册失败：" + e.getMessage());
                    }});
        }
    }
}
