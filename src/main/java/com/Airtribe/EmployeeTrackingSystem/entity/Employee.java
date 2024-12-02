package com.Airtribe.EmployeeTrackingSystem.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private Long employeeId;
    private String employeeName;
    private String employeeEmail;

    @ManyToOne
    @JoinColumn(name = "department_id")  // Foreign key to Department
    private Department department;

    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")) // Foreign key to Project
    private List<Project> project = new ArrayList<>();

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Project> getProject() {
        return project;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }

    public Employee() {
    }

    public Employee(long employeeId, String employeeName, String employeeEmail,Department department) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.department= department;
    }
}
