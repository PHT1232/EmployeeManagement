package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DepartmentRepository extends Reposistory<Departments> {
    public DepartmentRepository() {
        super();
    }


    public Departments findDepartmentByEmployeeId(int id) {
        Departments departments = null;
        try {
            String sql = "SELECT * FROM departments INNER JOIN department_assignments ON departments.department_id = department_assignments.department_id WHERE department_assignments.employee_id = " +  id;

            try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    departments = rowMapper.mapRow(rs);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return departments;
    }
}
