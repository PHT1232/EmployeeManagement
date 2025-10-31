package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.Connection;

public class EmployeeRepository extends Reposistory<Employee> {
    public EmployeeRepository() {
        super();
    }

    public EmployeeRepository Mapper(RowMapper<Employee> rowmapper) {
        this.rowMapper = rowmapper;
        return this;
    }

    public EmployeeRepository TableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public EmployeeRepository DatabaseConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public EmployeeRepository build() {
        return this;
    }

    public int insert(Employee employee) throws Exception {
        return dataBaseInsert(employee);
    }
}
