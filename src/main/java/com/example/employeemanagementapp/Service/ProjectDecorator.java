package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Entities.ProjectAssignments;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Mapper.ProjectAssignmentMapper;
import com.example.employeemanagementapp.Models.ProjectDisplay;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;
import com.example.employeemanagementapp.Repositories.ProjectAssignmentRepository;

import java.util.List;

public class ProjectDecorator {
    private ProjectAssignmentRepository repository;

    public ProjectDecorator() {
        repository = (ProjectAssignmentRepository) new ProjectAssignmentRepository()
                .Mapper(new ProjectAssignmentMapper())
                .TableName("projectassignments")
                .build();
    }

    public void addNumberOfEmployee(ProjectDisplay projectDisplay, int id) throws Exception {
        int num = repository.countEmployeeByProjectId(id);
        projectDisplay.setNumberOfEmployee(num + " members");
    }

    public void addProjectAssignment(int id, List<Employee> list) {
        list.forEach(employee -> {
            try {
                ProjectAssignments projectAssignments = new ProjectAssignments();
                projectAssignments.setProject_id(id);
                projectAssignments.setEmployee_id(employee.getEmployee_id());

                repository.insert(projectAssignments);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public int deleteEmployeeFromProject(ProjectAssignments projectAssignments) {
        try {
            return repository.delete(projectAssignments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getEmployee(int id) throws Exception {
        return repository.getEmployeeByProjectId(id);
    }
}
