package com.Airtribe.EmployeeTrackingSystem.service;

import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.exception.DataAlreadyExistException;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;
import com.Airtribe.EmployeeTrackingSystem.repository.DepartmentRepo;
import com.Airtribe.EmployeeTrackingSystem.repository.ProjectsRepo;
import com.Airtribe.EmployeeTrackingSystem.serviceInterface.IProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsService implements IProjectService {

    @Autowired
    private ProjectsRepo projectsRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String PROJECT_CACHE_KEY = "PROJECT";

    @Override
    public Project addProject(Project project) throws DataAlreadyExistException {
        Project pro = getProjectById(project.getProjectId());
        if(pro != null)
        {
            throw new DataAlreadyExistException("Project already exists with id"+ pro.getProjectId());
        }
        else {
            redisTemplate.opsForHash().put(PROJECT_CACHE_KEY, project.getProjectId(), project);
            projectsRepo.save(project);
            return project;
        }
    }

    @Override
    public Project updateProject(Long projectId, Project project) throws ResourceNotFoundException {
        if(!projectsRepo.existsByProjectId(projectId))
            throw new ResourceNotFoundException("Project not found with id"+ projectId);
        else
        {
            Project dbProject = projectsRepo.findByProjectId(projectId).orElse(null);
            if(dbProject != null) {
                dbProject.setProjectName(project.getProjectName());
                dbProject.setBudget(project.getBudget());
                dbProject.setDepartment(project.getDepartment());
                dbProject.setEmployees(project.getEmployees());
                projectsRepo.save(dbProject);
                redisTemplate.opsForHash().delete(PROJECT_CACHE_KEY, projectId);
                redisTemplate.opsForHash().put(PROJECT_CACHE_KEY, projectId, dbProject);
                return project;
            }
            return null;
        }
    }

    @Override
    @Transactional
    public String deleteProject(Long projectId) throws ResourceNotFoundException {
        if(!projectsRepo.existsByProjectId(projectId))
            throw new ResourceNotFoundException("Project not found with id"+ projectId);
        else {
            projectsRepo.deleteByProjectId(projectId);
            redisTemplate.opsForHash().delete(PROJECT_CACHE_KEY, projectId);
            return "Project deleted";
        }
    }

    @Override
    public Project getProjectById(Long projectId) {
        Project project = (Project) redisTemplate.opsForHash().get(PROJECT_CACHE_KEY, projectId);
        if(project != null)
            return project;
        else
            return projectsRepo.findByProjectId(projectId).orElse(null);
    }

    @Override
    public List<Project> getProjects() throws ResourceNotFoundException {
        List<Object> projects = redisTemplate.opsForHash().values(PROJECT_CACHE_KEY);
        if(projects != null)
            return (List<Project>) (List<?>) projects;
        else{
            List<Project> pro = projectsRepo.findAll();
            if(pro == null || pro.size()<=0)
                throw new ResourceNotFoundException("No projects found!");
            return pro;
        }
    }

    @Override
    public List<Project> getProjectByDepartmentId(Long departmentId) throws ResourceNotFoundException {
        List<Project> pro = projectsRepo.findByDepartment_DepartmentId(departmentId);
        if(pro == null || pro.size()<=0)
            throw new ResourceNotFoundException("No projects found in department with id "+ departmentId);
        return pro;
    }

    @Override
    public List<Project> getProjectsWithoutDepartment() throws ResourceNotFoundException {
        List<Project> pro = projectsRepo.findByDepartmentIdIsNull();
        if(pro == null || pro.size()<=0)
            throw new ResourceNotFoundException("No projects found without department");
        return pro;

    }

    @Override
    public Project getProjectByEmployeeId(Long employeeId) throws ResourceNotFoundException {
        Project pro = projectsRepo.findByEmployeeId(employeeId);
        if (pro == null)
            throw new ResourceNotFoundException("No project found for employee with id "+ employeeId);
        return pro;
    }

    @Override
    public Project getProjectByName(String projectName) throws ResourceNotFoundException {
        Project pro = projectsRepo.findByProjectName(projectName);
        if (pro == null)
            throw new ResourceNotFoundException("No project found with name "+ projectName);
        return pro;
    }
}
