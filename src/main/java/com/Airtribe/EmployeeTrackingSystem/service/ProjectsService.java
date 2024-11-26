package com.Airtribe.EmployeeTrackingSystem.service;

import com.Airtribe.EmployeeTrackingSystem.entity.Department;
import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.repository.DepartmentRepo;
import com.Airtribe.EmployeeTrackingSystem.repository.ProjectsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsService {

    @Autowired
    private ProjectsRepo projectsRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    public Project addProject(Project project) {
//        if(projectsRepo.existsById(project.getProjectId()))
//            return null;
//        else
            return projectsRepo.save(project);
    }

    public Project updateProject(Long projectId, Project project) {
        if(projectsRepo.existsById(projectId))
            return null;
        else
            return projectsRepo.save(project);
    }
    public String deleteProject(Long projectId) {
        if(!projectsRepo.existsById(projectId))
            return "Project not found";
        else {
            projectsRepo.deleteById(projectId);
            return "Project deleted";
        }
    }
    public Project getProjectById(Long projectId) {
        return projectsRepo.findById(projectId).orElse(null);
    }
    public List<Project> getProjects() {
        return projectsRepo.findAll();
    }
    public List<Project> getProjectByDepartmentId(Long departmentId) {
        return projectsRepo.findByDepartmentId(departmentId);
    }
    public List<Project> getProjectsWithoutDepartment() {
        return projectsRepo.findByDepartmentIdIsNull();
    }
    public Project getProjectByEmployeeId(Long employeeId) {
        return projectsRepo.findByEmployeeId(employeeId);
    }
    public Project getProjectByName(String projectName) {
        return projectsRepo.findByProjectName(projectName);
    }
}
