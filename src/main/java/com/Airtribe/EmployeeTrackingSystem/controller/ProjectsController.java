package com.Airtribe.EmployeeTrackingSystem.controller;

import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectsController {
        @Autowired
        private ProjectsService projectService;

        // Add a project
        @PostMapping
        public ResponseEntity<Project> addProject(@RequestBody Project project) {
                Project p = projectService.addProject(project);
                return new ResponseEntity<>(p, HttpStatus.CREATED);
        }

        // Update a project
        @PutMapping("/{projectId}")
        public ResponseEntity<Project> updateProject(@PathVariable("projectId") Long projectId, @RequestBody Project project) {
                Project p =  projectService.updateProject(projectId, project);
                return new ResponseEntity<>(p, HttpStatus.OK);
        }

        // Delete a project
        @DeleteMapping("/{projectId}")
        public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId) {
                String str = projectService.deleteProject(projectId);
                return new ResponseEntity<>(str, HttpStatus.NO_CONTENT);
        }

        // Get project by ID
        @GetMapping("/{projectId}")
        public ResponseEntity<Project> getProjectById(@PathVariable("projectId") Long projectId) {
                Project project = projectService.getProjectById(projectId);
                return project != null ?
                        new ResponseEntity<>(project, HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Get all projects
        @GetMapping
        public ResponseEntity<List<Project>> getProjects() {
                List<Project> projects = projectService.getProjects();
                return new ResponseEntity<>(projects, HttpStatus.OK);
        }

        // Get project by department
        @GetMapping("/departmentId")
        public ResponseEntity<List<Project>> getProjectByDepartmentId(@RequestParam("departmentId") Long departmentId) {
                List<Project> projects = projectService.getProjectByDepartmentId(departmentId);
                return projects.isEmpty() ?
                        new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                        new ResponseEntity<>(projects, HttpStatus.OK);
        }

        // Get projects with no department
        @GetMapping("/projectsWithoutDepartment")
        public List<Project> getProjectsWithoutDepartment() {
                return projectService.getProjectsWithoutDepartment();
        }

        // Get project by employee
        @GetMapping("/employeeId")
        public ResponseEntity<Project> getProjectByEmployeeId(@RequestParam("employeeId") Long employeeId) {
                Project project = projectService.getProjectByEmployeeId(employeeId);
                return project != null ?
                        new ResponseEntity<>(project, HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Get project by name
        @GetMapping("/projectName")
        public ResponseEntity<Project> getProjectByName(@RequestParam("projectName") String projectName) {
                Project project = projectService.getProjectByName(projectName);
                return project != null ?
                        new ResponseEntity<>(project, HttpStatus.OK):
                        new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}
