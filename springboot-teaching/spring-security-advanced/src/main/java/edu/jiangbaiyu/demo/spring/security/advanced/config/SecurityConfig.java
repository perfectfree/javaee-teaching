package edu.jiangbaiyu.demo.spring.security.advanced.config;

import edu.jiangbaiyu.demo.spring.security.advanced.handler.CustomAuthenticationFailureHandler;
import edu.jiangbaiyu.demo.spring.security.advanced.handler.CustomAuthenticationSuccessHandler;
import edu.jiangbaiyu.demo.spring.security.advanced.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ==================== API 安全配置（前后端分离，返回JSON） ====================
    @Bean
    @Order(1)  // 优先级最高，匹配 /api/**
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")   // 仅对 /api/* 路径生效
                .csrf(csrf -> csrf.disable()) // API不需要CSRF
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/login", "/api/logout", "/public/**").permitAll()  // 允许公开路径
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(new CustomAuthenticationSuccessHandler())   // 自定义JSON成功处理器
                        .failureHandler(new CustomAuthenticationFailureHandler())   // 自定义JSON失败处理器
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"code\":200,\"message\":\"注销成功\"}");
                        })
                        .permitAll()
                );

        return http.build();
    }

    // ==================== Web 安全配置（Thymeleaf页面，重定向） ====================
    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()  // 静态资源
                        .requestMatchers("/login", "/error").permitAll()  // 登录页、错误页公开
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")                // 自定义登录页面
                        .loginProcessingUrl("/login")       // 表单提交地址
                        .defaultSuccessUrl("/home", true)   // 登录成功后重定向到 /home
                        .failureUrl("/login?error=true")    // 失败重定向带参数
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecretKey2026")
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(86400)        // 24小时
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true")
                );
        // Web环境启用CSRF（Thymeleaf表单自动添加_token）
        return http.build();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}