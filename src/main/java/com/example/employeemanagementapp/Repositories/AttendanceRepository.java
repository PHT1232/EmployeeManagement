package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Attendance;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository extends Reposistory<Attendance> {
    public AttendanceRepository() {
        super();
    }

    public List<Attendance> getAttendanceWithLimit(int limit) throws Exception {
        List<Attendance> attendance = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        String sql = "SELECT * FROM " + tableName  + " WHERE attendance_date = '" + localDate + "'" + " ORDER BY attendance_id DESC LIMIT " + limit;

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                attendance.add(rowMapper.mapRow(rs));
            }
        }

        return attendance;
    }
}
