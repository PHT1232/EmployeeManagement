package com.example.employeemanagementapp.Mapper;

import com.example.employeemanagementapp.Builders.ProjectBuilder;
import com.example.employeemanagementapp.Entities.Projects;
import com.example.employeemanagementapp.Factories.EntityFactory;
import com.example.employeemanagementapp.Factories.EntityFactoryImpl;

import java.sql.ResultSet;

public class ProjectMapper implements RowMapper<Projects> {
    @Override
    public Projects mapRow(ResultSet resultSet) throws Exception {
        EntityFactory entityFactory = new EntityFactoryImpl();
        Projects projects = entityFactory.getBuilder(ProjectBuilder.class, new ProjectBuilder())
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
