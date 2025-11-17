package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Adapters.DatabaseObjectToMonthlyStatsAdapter;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Models.MonthlyStats;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;


import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private static EmployeeRepository employeeReposistory;
    private static PaginationService<Employee> paginationService ;

    public EmployeeService() throws Exception {
        employeeReposistory = (EmployeeRepository) new EmployeeRepository()
                .Mapper(new EmployeeMapper())
                .TableName("employees").build();

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

    public Employee findById(int id) {
        Employee employee;
        try {
            employee = employeeReposistory.findById(id, "employee_id");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return employee;
    }

    public List<Employee> searchEmployee(String name) {
        List<Employee> list = new ArrayList<>();
        try {
            list = employeeReposistory.searchByName(name, "first_name");
        } catch (Exception ex) {
            ex.printStackTrace();
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
