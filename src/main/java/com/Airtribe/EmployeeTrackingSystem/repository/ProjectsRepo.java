package com.Airtribe.EmployeeTrackingSystem.repository;

import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectsRepo extends JpaRepository<Project, Long> {
    List<Project> findByDepartmentId(Long departmentId);

    Project findByEmployeeId(Long employeeId);

    Project findByProjectName(String projectName);

    List<Project> findByDepartmentIdIsNull();

    boolean existsByProjectId(Long projectId);

    Optional<Project> findByProjectId(Long projectId);

    @Transactional
    void deleteByProjectId(Long projectId);
}
