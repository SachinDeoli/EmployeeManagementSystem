package com.Airtribe.EmployeeTrackingSystem.repository;

import com.Airtribe.EmployeeTrackingSystem.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);

    List<Employee> findByProjectId(Long projectId);

    Employee findByEmployeeName(String employeeName);

    List<Employee> findByProjectIsEmpty();

    boolean existsByEmployeeId(Long employeeId);

    Optional<Employee> findByEmployeeId(Long employeeId);

    @Transactional
    void deleteByEmployeeId(Long employeeId);
}
