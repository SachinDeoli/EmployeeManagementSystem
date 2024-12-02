package com.Airtribe.EmployeeTrackingSystem.controller;

import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;
import com.Airtribe.EmployeeTrackingSystem.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assign")
public class ManagementController {

    @Autowired
    private ManagementService managementService;

    // Assign project to department
    @PostMapping("/assignProjectToDepartment")
    @PreAuthorize("hasRole('ADMIN')")
    public void assignProjectToDepartment(@RequestParam Long projectId, @RequestParam Long departmentId)  throws ResourceNotFoundException{
        managementService.assignProjectToDepartment(projectId, departmentId);
    }

    // Assign employee to department
    @PostMapping("/assignEmployeeToDepartment")
    @PreAuthorize("hasRole('ADMIN')")
    public void assignEmployeeToDepartment(@RequestParam Long employeeId, @RequestParam Long departmentId)  throws ResourceNotFoundException {
        managementService.assignEmployeeToDepartment(employeeId, departmentId);
    }

    // Assign employee to project
    @PostMapping("/assignEmployeeToProject")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void assignEmployeeToProject(@RequestParam Long employeeId, @RequestParam Long projectId)  throws ResourceNotFoundException{
        managementService.assignEmployeeToProject(employeeId, projectId);
    }

    // Get total budget for a department
    @GetMapping("/totalBudgetForDepartment")
    @PreAuthorize("hasRole('ADMIN')")
    public Double getTotalBudgetForDepartment(@RequestParam Long departmentId)  throws ResourceNotFoundException{
        return managementService.getTotalBudgetForDepartment(departmentId);
    }
}
