package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Mapper.Top10EmployeeMapper;
import com.example.employeemanagementapp.Models.EmployeeDisplay;
import com.example.employeemanagementapp.Models.Top10EmployeeDisplay;
import com.example.employeemanagementapp.Models.WageDisplay;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;


import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
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

    public List<Employee> fetchPaginationWithDifferentId(int numOfRow, int offset, int[] employeesId) {
        try {
            return employeeReposistory.fetchPaginationWithDifferentId(numOfRow, offset, employeesId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EmployeeDisplay> fetchDisplayList(int numOfRows, int page) throws Exception {
        List<Employee> list = paginationService.fetchData(numOfRows, page);
        List<EmployeeDisplay> displayList = new ArrayList<>();

        list.forEach(employee -> {
            try {
                LocalDate localDate = LocalDate.now();
                Period period = Period.between(employee.getHire_date().toLocalDate(), localDate);
                EmployeeDisplay employeeDisplay = new EmployeeDisplay.Builder()
                        .EmployeeId(employee.getEmployee_id())
                        .EmployeeName(employee.getFirst_name() + " " + employee.getLast_name())
                                .Contact(employee.getEmail() + "\n" + employee.getPhone())
                                        .Position(employee.getPosition())
                        .Salary(employee.getSalary())
                                            .Hire_date(period.getYears() + " Years")
                                                .StartDate(employee.getHire_date()).build();

                displayList.add(employeeDisplay);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return displayList;
    }

    public int avgTenure() {
        int avgTenure = 0;

        try {
            List<Date> list = employeeReposistory.getHireDate();
            LocalDate localDate = LocalDate.now();
            String sql = "SELECT COUNT(1) as num FROM employees";

            for (Date date : list) {
                Period period = Period.between(date.toLocalDate(), localDate);
                avgTenure += period.getYears();
            }

            int totalRows = employeeReposistory.getNumberBySql(sql);

            avgTenure /= totalRows;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return avgTenure;
    }

    public List<WageDisplay> fetchEmployeeForWageDisplay(int numOfRows, int page) throws Exception {
        List<Employee> list = paginationService.fetchData(numOfRows, page);
        List<WageDisplay> displayList = new ArrayList<>();

        list.forEach(employee -> {
            try {
                EmployeeDecorator employeeDecorator = new EmployeeDecorator();

                WageDisplay wageDisplay = new WageDisplay.Builder()
                        .EmployeeId(employee.getEmployee_id())
                                .EmployeeName(employee.getLast_name() + " " + employee.getFirst_name())
                                        .Position(employee.getPosition())
                                                .BaseSalary(employee.getSalary())
                                                        .TotalCompensation(employee.getSalary() + employee.getBonus_hours_month() + employee.getProject_bonus_month())
                                                                .Bonus(employee.getBonus_hours_month() + employee.getProject_bonus_month()).build();

                employeeDecorator.addDepartmentName(wageDisplay, employee.getEmployee_id());

                displayList.add(wageDisplay);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return displayList;
    }

    public List<Top10EmployeeDisplay> getTop10Employee() throws Exception {
        List<Top10EmployeeDisplay> displayList = new ArrayList<>();
        List<Top10EmployeeDisplay> listFromDatabase = employeeReposistory.getTop10Employee();

        listFromDatabase.forEach(employee -> {
            try {
                EmployeeDecorator employeeDecorator = new EmployeeDecorator();
                employeeDecorator.addDepartmentName(employee, employee.getEmployeeId());

                displayList.add(employee);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return displayList;
    }

    public int getTotalEmployee() {
        int total = 0;
        String sql = "SELECT COUNT(1) as num FROM employees";

        try {
            total = employeeReposistory.getNumberBySql(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return total;
    }

    public int getTotalCompensation() {
        int total = 0;
        String sql = "SELECT SUM(salary) + SUM(bonus_hours_month) + SUM(project_bonus_month) as num FROM employees";

        try {
            total = employeeReposistory.getNumberBySql(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return total;
    }

    public int getTotalBonus() {
        int total = 0;
        String sql = "SELECT SUM(bonus_hours_month) + SUM(project_bonus_month) as num FROM employees";

        try {
            total = employeeReposistory.getNumberBySql(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return total;
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

    public int updateEmployee(Employee employee) throws Exception {
        return employeeReposistory.update(employee);
    }
}
