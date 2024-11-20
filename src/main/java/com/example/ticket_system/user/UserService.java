package com.example.ticket_system.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ticket_system.user.User;
import com.example.ticket_system.user.UserRepository;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 用户注册方法
    public User register(User user) {
        // 检查用户名是否已存在
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
        if (existingUser.isPresent()) {
            throw new RuntimeException("用户名已存在，请更换用户名后重新注册");
        }

        // 对用户输入的密码进行加密处理
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    // 用户登录验证方法
    public boolean login(String username, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    // 根据用户ID查询用户信息方法
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    // 更新用户信息方法
    public User updateUser(User updatedUser) {
        User existingUser = getUserById(updatedUser.getId());
        // 可根据业务需求，选择性地更新部分属性，避免全量覆盖导致原有重要信息丢失
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        return userRepository.save(existingUser);
    }

    // 修改密码方法
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("旧密码输入错误，无法修改密码");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
    }
}