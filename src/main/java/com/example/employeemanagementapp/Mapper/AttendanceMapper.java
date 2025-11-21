package com.example.employeemanagementapp.Mapper;

import com.example.employeemanagementapp.Entities.Attendance;

import java.sql.ResultSet;

public class AttendanceMapper implements RowMapper<Attendance> {
    @Override
    public Attendance mapRow(ResultSet resultSet) throws Exception {
        Attendance attendance = new Attendance.Builder()
                .Attendance_id(resultSet.getInt("attendance_id"))
                .Employee_id(resultSet.getInt("employee_id"))
                .Attendance_date(resultSet.getDate("attendance_date"))
                .Check_in(resultSet.getTimestamp("check_in"))
                .Check_out(resultSet.getTimestamp("check_out"))
                .Status(resultSet.getString("status"))
                .Notes(resultSet.getString("notes"))
                .Created_at(resultSet.getTimestamp("created_at"))
                .Updated_at(resultSet.getTimestamp("updated_at"))
                .build();

        return attendance;
    }
}
