package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.RowMapper;
import com.example.employeemanagementapp.Mapper.Top10EmployeeMapper;
import com.example.employeemanagementapp.Models.Top10EmployeeDisplay;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends Reposistory<Employee> {
    public EmployeeRepository() {
        super();
    }

    public List<Date> getHireDate() throws Exception {
        List<Date> list = new ArrayList<>();

        String sql = "SELECT hire_date as date FROM " + tableName;

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getDate("date"));
            }
        }

        return list;
    }

    public List<Top10EmployeeDisplay> getTop10Employee() throws Exception {
        List<Top10EmployeeDisplay> list = new ArrayList<>();
        String sql = "SELECT *,\n" +
                "       SUM(a.overtime) AS total_overtime\n" +
                "FROM employees e\n" +
                "JOIN attendance a ON e.employee_id = a.employee_id\n" +
                "GROUP BY e.employee_id\n" +
                "ORDER BY total_overtime DESC";

        RowMapper<Top10EmployeeDisplay> mapper = new Top10EmployeeMapper();
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapper.mapRow(rs));
            }
        }

        return list;
    }
}
