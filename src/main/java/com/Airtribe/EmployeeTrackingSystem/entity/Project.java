package com.Airtribe.EmployeeTrackingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(unique = true)
        private Long projectId;
        private String projectName;
        private double budget;

    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "projectId"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employee = new ArrayList<>();

        @ManyToOne
        @JoinColumn(name = "department_id", referencedColumnName = "departmentId")  // Foreign key to Department
        @JsonIgnore
        private Department department;

        public Project() {
        }

        public Project(Long projectId, String projectName, double budget, Department department) {
            this.projectId = projectId;
            this.projectName = projectName;
            this.budget = budget;
            this.department = department;
        }

        public Long getProjectId() {
            return projectId;
        }

        public void setProjectId(Long projectId) {
            this.projectId = projectId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public double getBudget() {
            return budget;
        }

        public void setBudget(double budget) {
            this.budget = budget;
        }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Employee> getEmployees() {
        return employee;
    }

    public void setEmployees(List<Employee> employee) {
        this.employee = employee;
    }
}
