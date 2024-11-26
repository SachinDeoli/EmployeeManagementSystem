package com.Airtribe.EmployeeTrackingSystem.service;

import com.Airtribe.EmployeeTrackingSystem.entity.Department;
import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.repository.DepartmentRepo;
import com.Airtribe.EmployeeTrackingSystem.repository.EmployeeRepo;
import com.Airtribe.EmployeeTrackingSystem.repository.ProjectsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagementService {
    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private DepartmentRepo departmentRepository;

    @Autowired
    private ProjectsRepo projectRepository;

    // Assign a project to a department
    public void assignProjectToDepartment(Long projectId, Long departmentId) {
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<Department> department = departmentRepository.findById(departmentId);

        if (project.isPresent() && department.isPresent()) {
            Project p = project.get();
            Department d = department.get();
            p.setDepartment(d);
            projectRepository.save(p);
        }
    }

    // Assign an employee to a department
    public void assignEmployeeToDepartment(Long employeeId, Long departmentId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        Optional<Department> department = departmentRepository.findById(departmentId);

        if (employee.isPresent() && department.isPresent()) {
            Employee e = employee.get();
            Department d = department.get();
            e.setDepartment(d);
            employeeRepository.save(e);
        }
    }

    // Assign an employee to a project
    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        Optional<Project> project = projectRepository.findById(projectId);

        if (employee.isPresent() && project.isPresent()) {
            Employee e = employee.get();
            Project p = project.get();
            e.getProject().add(p);
            employeeRepository.save(e);
        }
    }

    // Get total budget allocated to projects within a department
    public Double getTotalBudgetForDepartment(Long departmentId) {
        List<Project> projects = projectRepository.findByDepartmentId(departmentId);
        return projects.stream().mapToDouble(Project::getBudget).sum();
    }
}
