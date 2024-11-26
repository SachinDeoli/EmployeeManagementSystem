package com.Airtribe.EmployeeTrackingSystem.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String projectName;
        private double budget;

//        @OneToMany(mappedBy = "project")
//        private List<Employee> employee;

    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employee = new ArrayList<>();

        @ManyToOne
        @JoinColumn(name = "department_id")  // Foreign key to Department
        private Department department;

        public Project() {
        }

        public Project(Long id, String projectName, double budget, Department department) {
            this.id = id;
            this.projectName = projectName;
            this.budget = budget;
            this.department = department;
        }

        public Long getProjectId() {
            return id;
        }

        public void setProjectId(Long id) {
            this.id = id;
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
