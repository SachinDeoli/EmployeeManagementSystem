package com.Airtribe.EmployeeTrackingSystem.service;

import com.Airtribe.EmployeeTrackingSystem.entity.Department;
import com.Airtribe.EmployeeTrackingSystem.entity.Project;
import com.Airtribe.EmployeeTrackingSystem.repository.DepartmentRepo;
import com.Airtribe.EmployeeTrackingSystem.repository.ProjectsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private ProjectsRepo projectsRepo;

    public Department addDepartment(Department department) {
//        if(departmentRepo.existsById(department.getDepartmentId()))
//            return null;
//        else
            return departmentRepo.save(department);
    }

    public Department updateDepartment(Long departmentId, Department department) {
        if(departmentRepo.existsById(departmentId))
            return null;
        else
            return departmentRepo.save(department);
    }

    public String deleteDepartment(long departmentId) {
        if(!departmentRepo.existsById(departmentId))
            return "Department not found";
        else {
            departmentRepo.deleteById(departmentId);
            return "Department deleted";
        }
    }

    public Department getDepartmentById(long departmentId) {
        Optional<Department> dep =  departmentRepo.findById(departmentId);
        if(dep.isPresent())
            return dep.get();
        else
            return null;
    }

    public Department getDepartmentByName(String departmentName) {
        Optional<Department> dep =  departmentRepo.findByDepartmentName(departmentName);
        if(dep.isPresent())
            return dep.get();
        else
            return null;
    }

    public List<Department> getDepartments() {
       return departmentRepo.findAll();
    }

//    public Department assignProjectToDepartment(Long departmentId, Long projectId) {
//        Department dep = departmentRepo.findById(departmentId).orElse(null);
//        Project project = projectsRepo.findById(projectId).orElse(null);
//        if(dep != null && project != null) {
//            dep.getProjects().add(project);
//            departmentRepo.save(dep);
////            project.setDepartment(dep);
////            projectsRepo.save(project);
//            return dep;
//        }
//        return null;
//    }
}
