package com.example.employeemanagementapp.Mapper;

import com.example.employeemanagementapp.Builders.DepartmentBuilder;
import com.example.employeemanagementapp.Entities.Departments;

import java.sql.ResultSet;

public class DepartmentMapper implements RowMapper<Departments> {
    @Override
    public Departments mapRow(ResultSet resultSet) throws Exception {
        Departments departments = new DepartmentBuilder()
                .Department_id(resultSet.getInt("department_id"))
                .Department_name(resultSet.getString("department_name"))
                .Manager_id(resultSet.getInt("manager_id"))
                .Updated_at(resultSet.getDate("updated_at"))
                .Created_at(resultSet.getDate("created_at"))
                .build();

        return departments;
    }
}
