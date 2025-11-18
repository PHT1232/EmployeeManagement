package com.example.employeemanagementapp.Mapper;

import com.example.employeemanagementapp.Entities.Projects;

import java.sql.ResultSet;

public class ProjectMapper implements RowMapper<Projects> {
    @Override
    public Projects mapRow(ResultSet resultSet) throws Exception {
        Projects projects = new Projects.Builder()
                .Project_id(resultSet.getInt("project_id"))
                .Project_name(resultSet.getString("project_name"))
                .Description(resultSet.getString("description"))
                .End_date(resultSet.getDate("end_date"))
                .Start_date(resultSet.getDate("start_date"))
                .Created_at(resultSet.getDate("created_at"))
                .Updated_at(resultSet.getDate("updated_at"))
                .build();

        return projects;
    }
}
