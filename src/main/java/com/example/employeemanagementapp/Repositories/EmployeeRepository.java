package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public double fetchProjectBonusMonth(int id) throws Exception {
        String sql = "SELECT project_bonus_month FROM Employees WHERE employee_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            double bonusMonth = rs.getDouble(0);
            return bonusMonth;
        }
    }

    public List<Employee> fetchTop10() throws Exception {
        String sql = "SELECT employee_id, CONCAT(first_name, ' ', last_name) AS name, bonus_hours_month " +
                "FROM employees " +
                "ORDER BY bonus_hours_month DESC " +
                "LIMIT 10";

        List<Employee> list = new ArrayList<>();
        try (Statement statement = connection.createStatement();ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
        }

        return list;
    }
}
