package com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                //.csrf().disable()
//                .authorizeHttpRequests(authz -> authz
//                        // Admin access: full access to CRUD operations and administrative functions
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//
//                        // Manager access: can view and modify employees and projects within their department
//                        .requestMatchers("/manager/**").hasRole("MANAGER")
//
//                        // Employee access: only view their own profile and assigned projects
//                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")
//
//                        // Public endpoints (e.g., login, signup)
//                        .requestMatchers("/login", "/register").permitAll()
//
//                        // Fallback: All other requests need to be authenticated
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/register/success", true)
//                        .permitAll());
//                //.formLogin().permitAll() // Allows all users to login
//                //.httpBasic(); // Basic HTTP Authentication (if needed)
//
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorizeRequests ->  authorizeRequests.requestMatchers("/register", "/login", "/signin")
                    .permitAll().anyRequest().authenticated())
            .formLogin(formLogin -> formLogin.defaultSuccessUrl("/success", true).permitAll());

    return http.build();
}
}
