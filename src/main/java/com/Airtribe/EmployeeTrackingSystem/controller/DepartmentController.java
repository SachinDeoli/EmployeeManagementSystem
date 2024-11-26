package com.Airtribe.EmployeeTrackingSystem.controller;

import com.Airtribe.EmployeeTrackingSystem.entity.Department;
import com.Airtribe.EmployeeTrackingSystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Add department
    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department dep = departmentService.addDepartment(department);
        return new ResponseEntity<>(dep, HttpStatus.CREATED);
    }

    // Update department
    @PutMapping("/{departmentId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("departmentId") Long departmentId, @RequestBody Department department) {
        Department dep = departmentService.updateDepartment(departmentId, department);
        return department != null ?
                new ResponseEntity<>(department, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // Delete department
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("departmentId") Long departmentId) {
        String str = departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>(str, HttpStatus.NO_CONTENT);
    }

    // Get department by ID
    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("departmentId") Long departmentId) {
        Department department = departmentService.getDepartmentById(departmentId);
        return department != null ?
                new ResponseEntity<>(department, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get department by name
    @GetMapping("/departmentName")
    public ResponseEntity<Department> getDepartmentByName(@RequestParam("departmentName") String departmentName) {
        Department department = departmentService.getDepartmentByName(departmentName);
        return department != null ?
                new ResponseEntity<>(department, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get all departments
    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

}
