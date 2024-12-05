package com.Airtribe.EmployeeTrackingSystem.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private Long departmentId;
    private String departmentName;
    private String departmentDescription;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    @OneToMany(mappedBy = "department")
    private List<Project> projects;

    public Department() {
    }

    public Department(Long id, Long departmentId, String departmentName, String departmentDescription) {
        this.id = id;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
