package com.example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/test")
    public String publicTest() {
        return "Public access successful";
    }

    @GetMapping("/private/test")
    @PreAuthorize("isAuthenticated()")
    public String privateTest() {
        return "Private access successful";
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminTest() {
        return "Admin access successful";
    }
}
