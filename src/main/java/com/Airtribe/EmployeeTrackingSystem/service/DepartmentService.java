package com.Airtribe.EmployeeTrackingSystem.service;

import com.Airtribe.EmployeeTrackingSystem.entity.Department;
import com.Airtribe.EmployeeTrackingSystem.exception.DataAlreadyExistException;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;
import com.Airtribe.EmployeeTrackingSystem.repository.DepartmentRepo;
import com.Airtribe.EmployeeTrackingSystem.serviceInterface.IDepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String DEPARTMENT_CACHE_KEY = "DEPARTMENT";

    @Override
    public Department addDepartment(Department department) throws DataAlreadyExistException {
        Department dep = getDepartmentById(department.getDepartmentId());
        if(dep != null)
        {
            throw new DataAlreadyExistException("Department already exists with id"+ department.getDepartmentId());
        }
        else
        {
            departmentRepo.save(department);
            redisTemplate.opsForHash().put(DEPARTMENT_CACHE_KEY, department.getDepartmentId(), department);
            return department;
        }
    }

    @Override
    public Department updateDepartment(Long departmentId, Department updatedDepartment) throws ResourceNotFoundException {
        if(departmentRepo.existsByDepartmentId(departmentId))
        {
            Department department = departmentRepo.findByDepartmentId(departmentId).orElse(null);
            if(department != null) {
                department.setDepartmentName(updatedDepartment.getDepartmentName());
                department.setDepartmentDescription(updatedDepartment.getDepartmentDescription());
                department.setEmployees(updatedDepartment.getEmployees());
                department.setProjects(updatedDepartment.getProjects());
                departmentRepo.save(department);
                redisTemplate.opsForHash().delete(DEPARTMENT_CACHE_KEY, departmentId);
                redisTemplate.opsForHash().put(DEPARTMENT_CACHE_KEY, departmentId, department);
                return department;
            }
        }
        throw new ResourceNotFoundException("Department not found with id"+ departmentId);
    }

    @Override
    @Transactional
    public String deleteDepartment(long departmentId) throws ResourceNotFoundException {
        if(!departmentRepo.existsByDepartmentId(departmentId))
            throw new ResourceNotFoundException("Department not found with id"+ departmentId);
        else {
            departmentRepo.deleteByDepartmentId(departmentId);
            redisTemplate.opsForHash().delete(DEPARTMENT_CACHE_KEY, departmentId);
            return "Department deleted with id"+ departmentId;
        }
    }

    @Override
    public Department getDepartmentById(long departmentId) {
        Department department = (Department) redisTemplate.opsForHash().get(DEPARTMENT_CACHE_KEY, departmentId);
        if(department != null)
            return department;
        Optional<Department> dep =  departmentRepo.findByDepartmentId(departmentId);
        if(dep.isPresent())
            return dep.get();
        else
            return null;
    }

    @Override
    public Department getDepartmentByName(String departmentName) throws ResourceNotFoundException {
        Optional<Department> dep =  departmentRepo.findByDepartmentName(departmentName);
        if(dep.isPresent())
            return dep.get();
        else
            throw new ResourceNotFoundException("Department not found with name"+ departmentName +"Please check the case, spaces and try again");
    }

    @Override
    public List<Department> getDepartments() throws ResourceNotFoundException {
        List<Object> departments = redisTemplate.opsForHash().values(DEPARTMENT_CACHE_KEY);
        if(departments != null)
            return (List<Department>)(List<?>) departments;

       List<Department> dep = departmentRepo.findAll();
       if (dep != null)
           return dep;
       else
           throw new ResourceNotFoundException("No departments found");
    }
}
