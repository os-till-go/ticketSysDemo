package com.example.ticket_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 注入用户详情服务，用于从数据库等数据源加载用户信息进行认证
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 配置密码编码器，这里使用BCryptPasswordEncoder对用户密码进行加密存储和验证
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 配置安全过滤链，定义访问规则、认证方式等安全相关逻辑
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)  // 添加这一行来使用 userDetailsService
                // 配置跨站请求伪造防护，使用默认的配置，推荐使用
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                // 配置请求的授权规则
                .authorizeHttpRequests(authorize -> authorize
                        // 允许公共资源路径（如静态资源、公开页面等）无需认证即可访问
                        .requestMatchers("/public/**", "/", "/login", "/register").permitAll()
                        // 其他所有请求都需要认证后才能访问
                        .anyRequest().authenticated()
                )
                // 配置表单登录
                .formLogin(formLogin -> formLogin
                        // 登录页面的URL路径
                        .loginPage("/login")
                        // 登录处理的URL路径（表单提交的目标地址）
                        .loginProcessingUrl("/login-process")
                        // 登录成功后的默认跳转页面
                        .defaultSuccessUrl("/home", true)
                        // 登录失败后的跳转页面
                        .failureUrl("/login?error=true")
                )
                // 配置登出逻辑
                .logout(logout -> logout
                        // 登出的URL路径
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        // 登出成功后的跳转页面
                        .logoutSuccessUrl("/")
                        // 使HttpSession失效
                        .invalidateHttpSession(true)
                );

        return http.build();
    }
}