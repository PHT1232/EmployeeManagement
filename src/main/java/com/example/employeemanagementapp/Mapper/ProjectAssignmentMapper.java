package com.example.employeemanagementapp.Mapper;

import com.example.employeemanagementapp.Entities.ProjectAssignments;

import java.sql.ResultSet;

public class ProjectAssignmentMapper implements RowMapper<ProjectAssignments> {

    @Override
    public ProjectAssignments mapRow(ResultSet resultSet) throws Exception {
        return new ProjectAssignments(resultSet.getInt("project_id"), resultSet.getInt("employee_id"), resultSet.getString("role"));
    }
}
