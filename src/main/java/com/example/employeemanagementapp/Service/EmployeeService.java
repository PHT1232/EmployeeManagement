package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Models.EmployeeDisplay;
import com.example.employeemanagementapp.Models.WageDisplay;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;


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

    public List<EmployeeDisplay> fetchDisplayList(int numOfRows, int page) throws Exception {
        List<Employee> list = paginationService.fetchData(numOfRows, page);
        List<EmployeeDisplay> displayList = new ArrayList<>();

        list.forEach(employee -> {
            try {
                EmployeeDisplay employeeDisplay = new EmployeeDisplay.Builder()
                        .EmployeeName(employee.getFirst_name() + employee.getLast_name())
                                .Contact(employee.getEmail() + "\n" + employee.getPhone())
                                        .Position(employee.getPosition())
                                                .StartDate(employee.getHire_date()).build();


                displayList.add(employeeDisplay);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return displayList;
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

    public int getTotalEmployee() {
        int total = 0;

        try {
            total = employeeReposistory.totalRows();
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

}
