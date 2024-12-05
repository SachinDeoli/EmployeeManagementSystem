package com.Airtribe.EmployeeTrackingSystem.serviceInterface;

import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import com.Airtribe.EmployeeTrackingSystem.exception.DataAlreadyExistException;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;

import java.util.List;

public interface IEmployeeService {
    Employee addEmployee(Employee employee) throws DataAlreadyExistException;
    Employee updateEmployee(Long employeeId, Employee updateEmployee) throws ResourceNotFoundException;
    String deleteEmployee(Long employeeId) throws ResourceNotFoundException;
    Employee getEmployeeById(Long employeeId);
    List<Employee> getEmployees() throws ResourceNotFoundException;
    List<Employee> getEmployeeByDepartmentId(Long departmentId) throws ResourceNotFoundException;
    List<Employee> getEmployeeByProjectId(Long projectId) throws ResourceNotFoundException;
    List<Employee> getEmployeesWithoutProject(Long departmentId) throws ResourceNotFoundException;
    Employee getEmployeeByName(String employeeName) throws ResourceNotFoundException;
}
