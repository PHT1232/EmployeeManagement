package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Adapters.DatabaseObjectToMonthlyStatsAdapter;
import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Entities.DepartmentAssignments;
import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Models.MonthlyStats;
import com.example.employeemanagementapp.Repositories.DepartmentAssignmentRepository;
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
    private static DepartmentAssignmentRepository departmentAssignmentRepository;
    private static PaginationService<Employee> paginationService ;

    public EmployeeService() throws Exception {
        employeeReposistory = (EmployeeRepository) new EmployeeRepository()
                .Mapper(new EmployeeMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("employees").build();

        departmentAssignmentRepository = (DepartmentAssignmentRepository) new DepartmentAssignmentRepository()
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("department_assignments").build();

        paginationService = new PaginationServiceImpl<>(employeeReposistory);
    }

    public List<Employee> fetchList(int numOfRows, int page) throws Exception {
        return paginationService.fetchData(numOfRows, page);
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

    public int addEmployee(Employee employee) throws Exception {
        return employeeReposistory.insert(employee);
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
