package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Attendance;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.Connection;

public class AttendanceRepository extends Reposistory<Attendance> {
    public AttendanceRepository() {
        super();
    }

    public AttendanceRepository Mapper(RowMapper<Attendance> rowmapper) {
        this.rowMapper = rowmapper;
        return this;
    }

    public AttendanceRepository TableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public AttendanceRepository DatabaseConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public AttendanceRepository build() {
        return this;
    }
}
