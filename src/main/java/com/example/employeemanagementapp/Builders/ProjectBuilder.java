package com.example.employeemanagementapp.Builders;

import com.example.employeemanagementapp.Entities.Projects;

import java.sql.Date;

public class ProjectBuilder extends Builder<Projects> {
    private int project_id;
    private String project_name;
    private String description;
    private Date start_date;
    private Date end_date;
    private Date created_at;
    private Date updated_at;

    public ProjectBuilder Project_id(int project_id) {
        this.project_id = project_id;
        return this;
    }

    public ProjectBuilder Project_name(String project_name) {
        this.project_name = project_name;
        return this;
    }

    public ProjectBuilder Description(String description) {
        this.description = description;
        return this;
    }

    public ProjectBuilder Start_date(Date start_date) {
        this.start_date = start_date;
        return this;
    }

    public ProjectBuilder End_date(Date end_date) {
        this.end_date = end_date;
        return this;
    }

    public ProjectBuilder Created_at(Date created_at) {
        this.created_at = created_at;
        return this;
    }

    public ProjectBuilder Updated_at(Date updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    @Override
    public Projects build() {
        Projects projects = new Projects();
        projects.setProject_id(project_id);
        projects.setProject_name(project_name);
        projects.setDescription(description);
        projects.setStart_date(start_date);
        projects.setEnd_date(end_date);
        projects.setCreated_at(created_at);
        projects.setUpdated_at(updated_at);

        return projects;
    }
}
