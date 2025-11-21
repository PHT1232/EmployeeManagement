package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Entities.Projects;
import com.example.employeemanagementapp.Mapper.ProjectMapper;
import com.example.employeemanagementapp.Models.ProjectDisplay;
import com.example.employeemanagementapp.Repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    private static ProjectRepository reposistory;
    private static PaginationService<Projects> paginationService ;

    public ProjectService() throws Exception {
        reposistory = (ProjectRepository) new ProjectRepository()
                .Mapper(new ProjectMapper())
                .TableName("projects").build();

        paginationService = new PaginationServiceImpl<>(reposistory);
    }

    public List<ProjectDisplay> fetchList(int numOfRows, int offset) throws Exception {
        List<Projects> list = paginationService.fetchData(numOfRows, offset);
        List<ProjectDisplay> displayList = new ArrayList<>();

        list.forEach(projects -> {
            try {
                ProjectDecorator projectDecorator = new ProjectDecorator();
                ProjectDisplay projectDisplay = new ProjectDisplay.Builder()
                        .ProjectId(projects.getProject_id())
                        .ProjectName(projects.getProject_name())
                        .StartDate(projects.getStart_date())
                        .EndDate(projects.getEnd_date())
                        .CommissionRate(projects.getCommission_rate())
                        .Revenue(projects.getTotal_revenue())
                        .Status(projects.isIs_finished() ? "active" : "upcoming")
                        .build();

                projectDecorator.addNumberOfEmployee(projectDisplay, projects.getProject_id());

                displayList.add(projectDisplay);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return displayList;

    }


    public int insert(Projects projects) throws Exception {
        return reposistory.insert(projects);
    }

    public void update(int id, List<Employee> list) throws Exception {
        ProjectDecorator projectDecorator = new ProjectDecorator();
        projectDecorator.addProjectAssignment(id, list);
    }
}
