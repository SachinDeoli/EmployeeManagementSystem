package com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.controller;

import com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.entity.User;
import com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.model.UserModel;
import com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.service.UserService;
import com.Airtribe.EmployeeTrackingSystem.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserModel userModel) {
        return userService.register(userModel);
    }

    @GetMapping("/success")
    public String loginSuccess() {
        return "Login successfully";
    }

    @PostMapping("/signin")
    public String signIn(@RequestParam String username, @RequestParam String password) {
        return userService.signIn(username, password);
    }
}
