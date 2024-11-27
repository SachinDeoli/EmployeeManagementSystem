package com.Airtribe.EmployeeTrackingSystem.serviceInterface;

public interface IManagementService {
    void assignProjectToDepartment(Long projectId, Long departmentId);
    void assignEmployeeToDepartment(Long employeeId, Long departmentId);
    void assignEmployeeToProject(Long employeeId, Long projectId);
    Double getTotalBudgetForDepartment(Long departmentId);
}
