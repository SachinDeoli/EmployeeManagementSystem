package com.Airtribe.EmployeeTrackingSystem.controller;

import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assign")
public class ManagementController {

    @Autowired
    private ManagementService managementService;

    // Assign project to department
    @PostMapping("/assignProjectToDepartment")
    public void assignProjectToDepartment(@RequestParam Long projectId, @RequestParam Long departmentId) {
        managementService.assignProjectToDepartment(projectId, departmentId);
    }

    // Assign employee to department
    @PostMapping("/assignEmployeeToDepartment")
    public void assignEmployeeToDepartment(@RequestParam Long employeeId, @RequestParam Long departmentId) {
        managementService.assignEmployeeToDepartment(employeeId, departmentId);
    }

    // Assign employee to project
    @PostMapping("/assignEmployeeToProject")
    public void assignEmployeeToProject(@RequestParam Long employeeId, @RequestParam Long projectId) {
        managementService.assignEmployeeToProject(employeeId, projectId);
    }

    // Get total budget for a department
    @GetMapping("/totalBudgetForDepartment")
    public Double getTotalBudgetForDepartment(@RequestParam Long departmentId) {
        return managementService.getTotalBudgetForDepartment(departmentId);
    }
}
