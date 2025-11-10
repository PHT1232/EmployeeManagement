package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Projects;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ProjectRepository extends Reposistory<Projects> {
    public ProjectRepository() {
        super();
    }

    public List<Projects> fetchCompletedProject() throws Exception{
        String sql = "SELECT p.project_id, p.total_revenue, p.commission_rate, COUNT(pa.employee_id) AS team_size " +
                "FROM Projects p " +
                "JOIN ProjectAssignments pa ON p.project_id = pa.project_id " +
                "WHERE p.total_revenue > 0 " +
                "GROUP BY p.project_id";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
        }
    }
}
