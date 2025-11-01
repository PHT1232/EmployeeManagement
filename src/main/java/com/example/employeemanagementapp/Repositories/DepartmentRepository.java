package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.Connection;

public class DepartmentRepository extends Reposistory<Departments> {
    public DepartmentRepository() {
        super();
    }

    public DepartmentRepository Mapper(RowMapper<Departments> rowmapper) {
        this.rowMapper = rowmapper;
        return this;
    }

    public DepartmentRepository TableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DepartmentRepository DatabaseConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public DepartmentRepository build() {
        return this;
    }
}
