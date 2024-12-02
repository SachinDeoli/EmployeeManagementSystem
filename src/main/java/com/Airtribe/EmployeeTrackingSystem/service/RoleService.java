package com.Airtribe.EmployeeTrackingSystem.service;

import com.Airtribe.EmployeeTrackingSystem.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private EmployeeRepo employeeRepository;

    public List<String> getRolesByEmail(String email) {
        return employeeRepository.findByEmployeeEmail(email)
                .map(employee -> List.of(employee.getRole().name())) // Assume a single role, adjust if multiple
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
