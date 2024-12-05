package com.Airtribe.EmployeeTrackingSystem.serviceInterface;

import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;

public interface IManagementService {
    void assignProjectToDepartment(Long projectId, Long departmentId) throws ResourceNotFoundException;
    void assignEmployeeToDepartment(Long employeeId, Long departmentId) throws ResourceNotFoundException;
    void assignEmployeeToProject(Long employeeId, Long projectId) throws ResourceNotFoundException;
    Double getTotalBudgetForDepartment(Long departmentId) throws ResourceNotFoundException;
}
