package com.example.employeemanagementapp.Mapper;

import com.example.employeemanagementapp.Models.Top10EmployeeDisplay;

import java.sql.ResultSet;

public class Top10EmployeeMapper implements RowMapper<Top10EmployeeDisplay> {

    @Override
    public Top10EmployeeDisplay mapRow(ResultSet resultSet) throws Exception {
        return new Top10EmployeeDisplay.Builder()
                .EmployeeId(resultSet.getInt("employee_id"))
                .EmployeeName(resultSet.getString("first_name") + " " + resultSet.getString("last_name"))
                .BonusAmount(String.valueOf(resultSet.getDouble("bonus_hours_month")))
                .OvertimeHours(String.valueOf(resultSet.getInt("total_overtime")))
                .build();
    }
}
