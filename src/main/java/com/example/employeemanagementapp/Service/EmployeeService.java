package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Adapters.DatabaseObjectToMonthlyStatsAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    private static EmployeeRepository employeeReposistory;

    public EmployeeService() throws Exception {
        employeeReposistory = (EmployeeRepository) new EmployeeRepository()
                .Mapper(new EmployeeMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("Employees").build();
    }


    private List<MonthlyStats> fetchMonthlyData(Date monthStart, Date monthEnd) {
        List<MonthlyStats> list = new ArrayList<>();

        try (ResultSet rs = employeeReposistory.fetchMonthlyData(monthStart, monthEnd)){
            DatabaseObjectToMonthlyStatsAdapter adapter = new DatabaseObjectToMonthlyStatsAdapter();
            list = adapter.convert(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void updateMonthlySalaries(List<MonthlyStats> list) throws Exception {
        Employee employee;
        for (MonthlyStats ms : list) {
            double basePay = ms.getBaseWage() * ms.getDaysQualified();
            double bonus = ms.getBonusHours() * (ms.getBaseWage() * 0.15);
            double totalSalary = basePay + bonus;

            double projectBonus = employeeReposistory.fetchProjectBonusMonth(ms.getEmployee_id());
            totalSalary += projectBonus;

            employee = new Employee.Builder()
                    .Employee_id(ms.getId())
                    .Total_hours_month(ms.getTotalHours())
                    .Bonus_hours_month(ms.getBonusHours())
                    .Salary(totalSalary)
                    .build();

            employeeReposistory.update(employee);
        }
    }

}
