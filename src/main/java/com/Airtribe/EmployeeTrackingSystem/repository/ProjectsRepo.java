package com.Airtribe.EmployeeTrackingSystem.repository;

import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectsRepo extends JpaRepository<Project, Long> {
    List<Project> findByDepartmentId(Long departmentId);

    Project findByEmployeeId(Long employeeId);

    Project findByProjectName(String projectName);

    List<Project> findByDepartmentIdIsNull();
}
