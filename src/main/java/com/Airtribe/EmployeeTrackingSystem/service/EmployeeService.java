package com.Airtribe.EmployeeTrackingSystem.service;


import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import com.Airtribe.EmployeeTrackingSystem.exception.DataAlreadyExistException;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;
import com.Airtribe.EmployeeTrackingSystem.repository.EmployeeRepo;
import com.Airtribe.EmployeeTrackingSystem.serviceInterface.IEmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String EMPLOYEE_CACHE_KEY = "EMPLOYEE";

    @Override
    public Employee addEmployee(Employee employee) throws DataAlreadyExistException {
        Employee emp = getEmployeeById(employee.getEmployeeId());
        if(emp != null)
        {
            throw new DataAlreadyExistException("Employee already exists with id"+ emp.getEmployeeId());
        }
        else {
            redisTemplate.opsForHash().put(EMPLOYEE_CACHE_KEY, employee.getEmployeeId(), employee);
            employeeRepo.save(employee);
            return employee;
        }
    }

    @Override
    public Employee updateEmployee(Long employeeId, Employee updateEmployee) throws ResourceNotFoundException {
        if(!employeeRepo.existsByEmployeeId(employeeId))
            throw new ResourceNotFoundException("Employee not found with id "+ employeeId);
        else
        {
            Employee dbEmployee = employeeRepo.findByEmployeeId(employeeId).orElse(null);
            if(dbEmployee != null) {
                //dbEmployee.setEmployeeId(employeeId);
                dbEmployee.setEmployeeName(updateEmployee.getEmployeeName());
                dbEmployee.setDepartment(updateEmployee.getDepartment());
                dbEmployee.setProject(updateEmployee.getProject());
                dbEmployee.setEmployeeEmail(updateEmployee.getEmployeeEmail());
                employeeRepo.save(dbEmployee);
                redisTemplate.opsForHash().delete(EMPLOYEE_CACHE_KEY, employeeId);
                redisTemplate.opsForHash().put(EMPLOYEE_CACHE_KEY, employeeId, dbEmployee);
                return updateEmployee;
            }
            return null;
        }
    }

    @Override
    @Transactional
    public String deleteEmployee(Long employeeId) throws ResourceNotFoundException {
        if(!employeeRepo.existsByEmployeeId(employeeId))
            throw new ResourceNotFoundException("Employee not found with id "+ employeeId);
        else {
            employeeRepo.deleteByEmployeeId(employeeId);
            redisTemplate.opsForHash().delete(EMPLOYEE_CACHE_KEY, employeeId);
            return "Employee deleted";
        }
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        Employee employee = (Employee) redisTemplate.opsForHash().get(EMPLOYEE_CACHE_KEY, employeeId);
        if(employee != null)
            return employee;
        else
            return employeeRepo.findByEmployeeId(employeeId).orElse(null);
    }

    @Override
    public List<Employee> getEmployees() throws ResourceNotFoundException {
        List<Object> employees = redisTemplate.opsForHash().values(EMPLOYEE_CACHE_KEY);
        if(employees != null)
            return (List<Employee>) (List<?>) employees;
        else {
            List<Employee> emp = employeeRepo.findAll();
            if(emp != null)
                return emp;
            else
                throw new ResourceNotFoundException("No employees found!");
        }
    }

    @Override
    public List<Employee> getEmployeeByDepartmentId(Long departmentId) throws ResourceNotFoundException{
        List<Employee> emp = employeeRepo.findByDepartmentId(departmentId);
        if(emp != null)
            return emp;
        else
            throw new ResourceNotFoundException("No employees found in department with id "+ departmentId);
    }

    @Override
    public List<Employee> getEmployeeByProjectId(Long projectId) throws ResourceNotFoundException {
        List<Employee> emp = employeeRepo.findByProjectId(projectId);
        if(emp != null)
            return emp;
        else
            throw new ResourceNotFoundException("No employees found in project with id "+ projectId);
    }

    @Override
    public List<Employee> getEmployeesWithoutProject(Long departmentId) throws ResourceNotFoundException {
        List<Employee> employees = employeeRepo.findByDepartmentId(departmentId);
        employees.removeIf(e -> !e.getProject().isEmpty() || e.getProject() != null);
        if(employees != null)
            return employees;
        else
            throw new ResourceNotFoundException("No employees found in department with depId "+ departmentId+" who have no project assigned");
    }

    @Override
    public Employee getEmployeeByName(String employeeName) throws ResourceNotFoundException {
        Employee emp = employeeRepo.findByEmployeeName(employeeName);
        if(emp != null)
            return emp;
        else
            throw new ResourceNotFoundException("No employees found by name "+ employeeName);
    }
}
