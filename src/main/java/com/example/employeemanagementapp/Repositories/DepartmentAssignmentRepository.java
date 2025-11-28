package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.DepartmentAssignments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentAssignmentRepository extends Reposistory<DepartmentAssignments> {
    public List<Employee> getEmployeeByDepartmentId(int id) throws Exception {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees INNER JOIN " + tableName + " ON employees.employee_id = " + tableName + ".employee_id WHERE " + tableName + ".department_id = " + id;
        RowMapper<Employee> employeeRowMapper = new EmployeeMapper();

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                list.add(employeeRowMapper.mapRow(rs));
            }
        }

        return list;
    }

    public int delete(DepartmentAssignments entity) throws Exception {
        String query = "DELETE FROM " + tableName + " WHERE department_id = " + entity.getDepartment_id() + " AND employee_id = " + entity.getEmployee_id();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return statement.executeUpdate();
        }
    }
}
