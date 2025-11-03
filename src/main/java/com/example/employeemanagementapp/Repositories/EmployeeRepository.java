package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.*;

public class EmployeeRepository extends Reposistory<Employee> {
    public EmployeeRepository() {
        super();
    }

    public ResultSet fetchMonthlyData(Date monthStart, Date monthEnd) throws Exception {
        String sql = "SELECT e.employee_id, " +
                "CONCAT(e.first_name, ' ', e.last_name) AS name, " +
                "e.salary AS base_wage, " +
                "a.check_in, a.check_out, a.attendance_date " +
                "FROM employees e " +
                "JOIN attendance a ON e.employee_id = a.employee_id " +
                "WHERE a.attendance_date BETWEEN ? AND ? " +
                "ORDER BY e.employee_id, a.attendance_date";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, monthStart);
            ps.setDate(2, monthEnd);

            return ps.executeQuery();
        }
    }
}
