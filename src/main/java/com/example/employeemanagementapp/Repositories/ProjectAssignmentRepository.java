package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.ProjectAssignments;

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
}
