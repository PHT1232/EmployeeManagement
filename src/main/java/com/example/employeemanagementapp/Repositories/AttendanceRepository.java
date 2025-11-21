package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Attendance;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AttendanceRepository extends Reposistory<Attendance> {
    public AttendanceRepository() {
        super();
    }

    public Attendance getFirstAttendance() throws Exception {
        Attendance attendance = null;
        String sql = "SELECT * FROM " + tableName + " ORDER BY attendance_id DESC LIMIT 1";

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                attendance = rowMapper.mapRow(rs);
            }
        }

        return attendance;
    }
}
