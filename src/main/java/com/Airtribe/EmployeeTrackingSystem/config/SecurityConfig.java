//package com.Airtribe.EmployeeTrackingSystem.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/employees/**").hasRole("ADMIN")
//                .antMatchers("/departments/**").hasRole("ADMIN")
//                .antMatchers("/projects/**").hasRole("MANAGER")
//                .antMatchers("/assign").hasAnyRole("ADMIN")
//                .and()
//                .oauth2Login()
//                .loginPage("/login");
//    }
//}
