package com.example.employeemanagementapp.Mapper;

import com.example.employeemanagementapp.Builders.AttendanceBuilder;
import com.example.employeemanagementapp.Entities.Attendance;
import com.example.employeemanagementapp.Factories.EntityFactory;
import com.example.employeemanagementapp.Factories.EntityFactoryImpl;

import java.sql.ResultSet;

public class AttendanceMapper implements RowMapper<Attendance> {
    @Override
    public Attendance mapRow(ResultSet resultSet) throws Exception {
        EntityFactory entityFactory = new EntityFactoryImpl();
        Attendance attendance = entityFactory.getBuilder(AttendanceBuilder.class, new AttendanceBuilder())
                .Attendance_id(resultSet.getInt("attendance_id"))
                .Employee_id(resultSet.getInt("employee_id"))
                .Attendance_date(resultSet.getDate("attendance_date"))
                .Check_in(resultSet.getDate("check_in"))
                .Check_out(resultSet.getDate("check_out"))
                .Status(resultSet.getString("status"))
                .Notes(resultSet.getString("notes"))
                .Created_at(resultSet.getDate("created_at"))
                .Updated_at(resultSet.getDate("updated_at"))
                .build();

        return attendance;
    }
}
