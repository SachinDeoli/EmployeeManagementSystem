package com.Airtribe.EmployeeTrackingSystem.serviceInterface;

import com.Airtribe.EmployeeTrackingSystem.entity.Department;
import com.Airtribe.EmployeeTrackingSystem.exception.DataAlreadyExistException;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;

import java.util.List;

public interface IDepartmentService {
    Department addDepartment(Department department) throws DataAlreadyExistException;
    Department updateDepartment(Long departmentId, Department updatedDepartment) throws ResourceNotFoundException;
    String deleteDepartment(long departmentId) throws ResourceNotFoundException;
    Department getDepartmentById(long departmentId);
    Department getDepartmentByName(String departmentName) throws ResourceNotFoundException;
    List<Department> getDepartments() throws ResourceNotFoundException;
}
