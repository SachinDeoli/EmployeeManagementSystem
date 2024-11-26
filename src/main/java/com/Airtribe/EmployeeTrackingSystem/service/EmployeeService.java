package com.Airtribe.EmployeeTrackingSystem.service;

import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import com.Airtribe.EmployeeTrackingSystem.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public Employee addEmployee(Employee employee) {
//        if(employeeRepo.existsById(employee.getEmployeeId()))
//            return null;
//        else
            return employeeRepo.save(employee);
    }
    public Employee updateEmployee(Long employeeId, Employee employee) {
        if(employeeRepo.existsById(employeeId))
            return null;
        else
            return employeeRepo.save(employee);
    }
    public String deleteEmployee(Long employeeId) {
        if(!employeeRepo.existsById(employeeId))
            return "Employee not found";
        else {
            employeeRepo.deleteById(employeeId);
            return "Employee deleted";
        }
    }
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepo.findById(employeeId).orElse(null);
    }
    public List<Employee> getEmployees() {
        return employeeRepo.findAll();
    }
    public List<Employee> getEmployeeByDepartmentId(Long departmentId) {
        return employeeRepo.findByDepartmentId(departmentId);
    }
    public List<Employee> getEmployeeByProjectId(Long projectId) {
        return employeeRepo.findByProjectId(projectId);
    }

    // Get employees under a department who have no project assigned
    public List<Employee> getEmployeesWithoutProject(Long departmentId) {
        List<Employee> employees = employeeRepo.findByDepartmentId(departmentId);
        employees.removeIf(e -> !e.getProject().isEmpty());
        return employees;
    }

    public Employee getEmployeeByName(String employeeName) {
        return employeeRepo.findByEmployeeName(employeeName);
    }
}
