package com.Airtribe.EmployeeTrackingSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to Employee Tracking System!";
    }

    @GetMapping("/api/hello")
    public String apiHello() {
        return "Hello World from API!";
    }
}