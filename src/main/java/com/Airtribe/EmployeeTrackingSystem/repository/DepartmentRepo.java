package com.Airtribe.EmployeeTrackingSystem.repository;

import com.Airtribe.EmployeeTrackingSystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    Optional<Department> findByDepartmentName(String departmentName);
}