package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Projects;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.Connection;

public class ProjectRepository extends Reposistory<Projects> {
    public ProjectRepository() {
        super();
    }

    public ProjectRepository Mapper(RowMapper<Projects> rowmapper) {
        this.rowMapper = rowmapper;
        return this;
    }

    public ProjectRepository TableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public ProjectRepository DatabaseConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public ProjectRepository build() {
        return this;
    }
}
