package com.Airtribe.EmployeeTrackingSystem.service;

import com.Airtribe.EmployeeTrackingSystem.entity.Department;
import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;
import com.Airtribe.EmployeeTrackingSystem.repository.DepartmentRepo;
import com.Airtribe.EmployeeTrackingSystem.repository.EmployeeRepo;
import com.Airtribe.EmployeeTrackingSystem.repository.ProjectsRepo;
import com.Airtribe.EmployeeTrackingSystem.serviceInterface.IManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagementService implements IManagementService {
    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private DepartmentRepo departmentRepository;

    @Autowired
    private ProjectsRepo projectRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String EMPLOYEE_CACHE_KEY = "EMPLOYEE";
    private static final String DEPARTMENT_CACHE_KEY = "DEPARTMENT";
    private static final String PROJECT_CACHE_KEY = "PROJECT";

    // Assign a project to a department
    @Override
    public void assignProjectToDepartment(Long projectId, Long departmentId) throws ResourceNotFoundException {
        Optional<Project> project = projectRepository.findByProjectId(projectId);
        Optional<Department> department = departmentRepository.findByDepartmentId(departmentId);

        if (project.isPresent() && department.isPresent()) {
            Project p = project.get();
            Department d = department.get();
            p.setDepartment(d);
            projectRepository.save(p);
            redisTemplate.opsForHash().delete(PROJECT_CACHE_KEY, projectId);
            redisTemplate.opsForHash().put(PROJECT_CACHE_KEY, projectId, p);
        }
        else {
            throw new ResourceNotFoundException("Wrong ProjectId or departmentID, Please recheck!");
        }
    }

    // Assign an employee to a department
    @Override
    public void assignEmployeeToDepartment(Long employeeId, Long departmentId) throws ResourceNotFoundException {
        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
        Optional<Department> department = departmentRepository.findByDepartmentId(departmentId);

        if (employee.isPresent() && department.isPresent()) {
            Employee e = employee.get();
            Department d = department.get();
            e.setDepartment(d);
            employeeRepository.save(e);
            redisTemplate.opsForHash().delete(EMPLOYEE_CACHE_KEY, employeeId);
            redisTemplate.opsForHash().put(EMPLOYEE_CACHE_KEY, employeeId, e);
        }
        else
        {
            throw new ResourceNotFoundException("Wrong EmployeeID or DepartmentID, Please recheck!");
        }
    }

    // Assign an employee to a project
    @Override
    public void assignEmployeeToProject(Long employeeId, Long projectId) throws ResourceNotFoundException {
        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
        Optional<Project> project = projectRepository.findByProjectId(projectId);

        if (employee.isPresent() && project.isPresent()) {
            Employee e = employee.get();
            Project p = project.get();
            e.getProject().add(p);
            employeeRepository.save(e);
            redisTemplate.opsForHash().delete(EMPLOYEE_CACHE_KEY, employeeId);
            redisTemplate.opsForHash().put(EMPLOYEE_CACHE_KEY, employeeId, e);
        }
        else {
            throw new ResourceNotFoundException("Wrong EmployeeID or ProjectID, Please recheck!");
        }
    }

    // Get total budget allocated to projects within a department
    @Override
    public Double getTotalBudgetForDepartment(Long departmentId) throws ResourceNotFoundException {
        List<Project> projects = projectRepository.findByDepartment_DepartmentId(departmentId);
        if(projects==null || projects.size()<=0)
        {
            throw new ResourceNotFoundException("No Projects found");
        }
        return projects.stream().mapToDouble(Project::getBudget).sum();
    }
}
