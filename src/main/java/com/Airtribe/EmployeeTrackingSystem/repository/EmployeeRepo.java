package com.Airtribe.EmployeeTrackingSystem.repository;

import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);

    List<Employee> findByProjectId(Long projectId);

    Employee findByEmployeeName(String employeeName);

    List<Employee> findByProjectIsEmpty();
}
