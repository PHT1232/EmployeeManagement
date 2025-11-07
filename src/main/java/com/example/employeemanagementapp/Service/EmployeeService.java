package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Builders.EmployeeBuilder;
import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Models.MonthlyStats;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;
import com.example.employeemanagementapp.Repositories.Reposistory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class EmployeeService {
    private static EmployeeRepository employeeReposistory;

    public EmployeeService() throws Exception {
        employeeReposistory = (EmployeeRepository) new EmployeeRepository()
                .Mapper(new EmployeeMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("Employees").build();
    }


    private Map<Integer, MonthlyStats> fetchMonthlyData(Date monthStart, Date monthEnd) {
        Map<Integer, MonthlyStats> statsMap = new HashMap<>();
        try (ResultSet rs = employeeReposistory.fetchMonthlyData(monthStart, monthEnd)){
            while (rs.next()) {
                int id = rs.getInt("employee_id");
                MonthlyStats ms = statsMap.getOrDefault(id, new MonthlyStats());
                ms.setId(id);
                ms.setName(rs.getString("name"));
                ms.setBaseWage(rs.getDouble("base_wage"));

                Timestamp checkIn = rs.getTimestamp("check_in");
                Timestamp checkOut = rs.getTimestamp("check_out");

                if (checkIn != null && checkOut != null) {
                    double hours = (checkOut.getTime() - checkIn.getTime()) / (1000.0 * 60 * 60);

                    if (hours >= 6.0) {
                        ms.increaseDaysQualified(1);
                        ms.setTotalHours(hours);
                        if (hours >= 7.0) {
                            ms.increaseBonusHours(hours - 6.0);
                        }
                    }
                }
                statsMap.put(id, ms);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return statsMap;
    }

    public void updateMonthlySalaries(Map<Integer, MonthlyStats> statsMap) {
        Employee employee = new Employee();

    }
}
