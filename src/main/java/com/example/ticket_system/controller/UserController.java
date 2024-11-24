package com.example.ticket_system.controller;

import com.example.ticket_system.module.user.dto.LoginRequest;
import com.example.ticket_system.module.user.UserRepository;
import com.example.ticket_system.module.user.dto.RegisterRequest;
import com.example.ticket_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.ticket_system.model.User;
import java.util.List;
import com.example.ticket_system.model.User;
import com.example.ticket_system.module.user.dto.LoginRequest;
import com.example.ticket_system.module.user.dto.UpdateProfileRequest;
import com.example.ticket_system.module.user.UserRepository;
import com.example.ticket_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;  // 添加 UserRepository

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateProfileRequest request) {
        User user = userService.updateProfile(userDetails.getUsername(), request);
        return ResponseEntity.ok(user);
    }

//    // 用户注册接口
//    @PostMapping("/register")
//    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
//        User user = new User();
//        user.setRegistrationInfo(
//                registerRequest.getUsername(),
//                registerRequest.getPassword(),
//                registerRequest.getEmail()
//        );
//        User registeredUser = userService.register(user);
//        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
//    }
//
//    // 用户登录接口
//    @PostMapping("/login")
//    public ResponseEntity<Boolean> login(@RequestBody LoginRequest loginRequest) {
//        boolean result = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    // 根据用户ID查询用户信息接口
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // 更新用户信息接口
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        User user = userService.updateUser(updatedUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // 修改密码接口
    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,
                                               @RequestParam String oldPassword,
                                               @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 查询所有启用状态用户的接口
    @GetMapping("/enabled")
    public ResponseEntity<List<User>> getEnabledUsers() {
        List<User> users = userRepository.findByEnabledTrue();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // 根据关键字模糊查询用户接口
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        List<User> users = userRepository.findByKeyword(keyword);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}