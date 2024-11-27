package com.Airtribe.EmployeeTrackingSystem.serviceInterface;

import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.exception.DataAlreadyExistException;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;

import java.util.List;

public interface IProjectService {
    Project addProject(Project project) throws DataAlreadyExistException;
    Project updateProject(Long projectId, Project project) throws ResourceNotFoundException;
    String deleteProject(Long projectId) throws ResourceNotFoundException;
    Project getProjectById(Long projectId);
    List<Project> getProjects() throws ResourceNotFoundException;
    List<Project> getProjectByDepartmentId(Long departmentId) throws ResourceNotFoundException;
    List<Project> getProjectsWithoutDepartment() throws ResourceNotFoundException;
    Project getProjectByEmployeeId(Long employeeId) throws ResourceNotFoundException;
    Project getProjectByName(String projectName) throws ResourceNotFoundException;
}
