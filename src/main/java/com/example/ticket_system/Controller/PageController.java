package com.example.ticket_system.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 跳转到 login.jsp 页面
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard"; // 跳转到 dashboard.jsp 页面
    }

    //... 其他页面跳转逻辑...
}

