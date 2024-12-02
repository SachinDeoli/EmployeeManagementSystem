package com.Airtribe.EmployeeTrackingSystem.controller;

import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.exception.DataAlreadyExistException;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;
import com.Airtribe.EmployeeTrackingSystem.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectsController {
        @Autowired
        private ProjectsService projectService;

        // Add a project
        @PostMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
        public ResponseEntity<Project> addProject(@RequestBody Project project) throws DataAlreadyExistException {
                Project p = projectService.addProject(project);
                return new ResponseEntity<>(p, HttpStatus.CREATED);
        }

        // Update a project
        @PutMapping("/{projectId}")
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
        public ResponseEntity<Project> updateProject(@PathVariable("projectId") Long projectId, @RequestBody Project project) throws ResourceNotFoundException{
                Project p =  projectService.updateProject(projectId, project);
                return new ResponseEntity<>(p, HttpStatus.OK);
        }

        // Delete a project
        @DeleteMapping("/{projectId}")
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
        public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId) throws ResourceNotFoundException{
                String str = projectService.deleteProject(projectId);
                return new ResponseEntity<>(str, HttpStatus.NO_CONTENT);
        }

        // Get project by ID
        @GetMapping("/{projectId}")
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
        public ResponseEntity<Project> getProjectById(@PathVariable("projectId") Long projectId) throws ResourceNotFoundException{
                Project project = projectService.getProjectById(projectId);
                return project != null ?
                        new ResponseEntity<>(project, HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Get all projects
        @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
        public ResponseEntity<List<Project>> getProjects() throws ResourceNotFoundException{
                List<Project> projects = projectService.getProjects();
                return new ResponseEntity<>(projects, HttpStatus.OK);
        }

        // Get project by department
        @GetMapping("/departmentId")
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
        public ResponseEntity<List<Project>> getProjectByDepartmentId(@RequestParam("departmentId") Long departmentId) throws ResourceNotFoundException{
                List<Project> projects = projectService.getProjectByDepartmentId(departmentId);
                return projects.isEmpty() ?
                        new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                        new ResponseEntity<>(projects, HttpStatus.OK);
        }

        // Get projects with no department
        @GetMapping("/projectsWithoutDepartment")
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
        public List<Project> getProjectsWithoutDepartment() throws ResourceNotFoundException{
                return projectService.getProjectsWithoutDepartment();
        }

        // Get project by employee
        @GetMapping("/employeeId")
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
        public ResponseEntity<Project> getProjectByEmployeeId(@RequestParam("employeeId") Long employeeId) throws ResourceNotFoundException{
                Project project = projectService.getProjectByEmployeeId(employeeId);
                return project != null ?
                        new ResponseEntity<>(project, HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Get project by name
        @GetMapping("/projectName")
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
        public ResponseEntity<Project> getProjectByName(@RequestParam("projectName") String projectName) throws ResourceNotFoundException {
                Project project = projectService.getProjectByName(projectName);
                return project != null ?
                        new ResponseEntity<>(project, HttpStatus.OK):
                        new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}
