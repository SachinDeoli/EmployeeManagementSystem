package com.Airtribe.EmployeeTrackingSystem.controller;

import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import com.Airtribe.EmployeeTrackingSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    // Add employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee emp = employeeService.addEmployee(employee);
        return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    // Update employee
    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("employeeId") Long employeeId, @RequestBody Employee employee) {
        Employee emp = employeeService.updateEmployee(employeeId, employee);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    // Delete employee
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        String str = employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(str, HttpStatus.NO_CONTENT);
    }

    // Get employee by ID
    //Can only be accessed by the employee
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return employee != null ?
                new ResponseEntity<>(employee, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Get employee by department
    @GetMapping("/departmentId")
    public ResponseEntity<List<Employee>> getEmployeeByDepartmentId(@RequestParam("departmentId") Long departmentId) {
        List<Employee> employees = employeeService.getEmployeeByDepartmentId(departmentId);
        return employees.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Get employee by project
    @GetMapping("/projectId")
    public ResponseEntity<List<Employee>> getEmployeeByProjectId(@RequestParam("projectId") Long projectId) {
        List<Employee> employees = employeeService.getEmployeeByProjectId(projectId);
        return employees.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Get employees under a department with no projects
    @GetMapping("/employeesWithoutProject")
    public List<Employee> getEmployeesWithoutProject(@RequestParam Long departmentId) {
        return employeeService.getEmployeesWithoutProject(departmentId);
    }

    // Get employee by name
    @GetMapping("/employeeName")
    public ResponseEntity<Employee> getEmployeeByName(@RequestParam("employeeName") String employeeName) {
        Employee employee = employeeService.getEmployeeByName(employeeName);
        return employee != null ?
                new ResponseEntity<>(employee, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
