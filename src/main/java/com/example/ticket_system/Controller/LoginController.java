package com.example.ticket_system.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes) {
        // 假设这里有验证用户登录的逻辑，验证成功后将用户信息存入会话
        if (isValidUser(username, password)) {
            session.setAttribute("username", username);
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "用户名或密码错误");
            return "redirect:/login";
        }
    }

    private boolean isValidUser(String username, String password) {
        // 这里应该是实际的用户验证逻辑，比如查询数据库验证用户信息
        return true;
    }

}