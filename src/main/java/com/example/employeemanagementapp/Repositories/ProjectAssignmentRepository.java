package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Entities.ProjectAssignments;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectAssignmentRepository extends Reposistory<ProjectAssignments> {
    public ProjectAssignmentRepository() {
        super();
    }

    public int countEmployeeByProjectId(int id) throws Exception {
        List<ProjectAssignments> list = new ArrayList<>();
        String sql = "SELECT COUNT(employee_id) AS num FROM " + tableName + " WHERE project_id = " + id;
        int numberOfEmployee = 0;

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                numberOfEmployee = rs.getInt("num");
            }
        }

        return numberOfEmployee;
    }

    public List<Employee> getEmployeeByProjectId(int id) throws Exception {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees INNER JOIN " + tableName + " ON employees.employee_id = " + tableName + ".employee_id WHERE " + tableName + ".project_id = " + id;
        RowMapper<Employee> employeeRowMapper = new EmployeeMapper();

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                list.add(employeeRowMapper.mapRow(rs));
            }
        }

        return list;
    }
}
