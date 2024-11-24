package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class DemoApplicationTests {
	// 可以在测试类中运行这段代码来生成加密密码
	@Test
	void contextLoads() {
		String encodedPassword = new BCryptPasswordEncoder().encode("password");
		System.out.println(encodedPassword);
	}

}
