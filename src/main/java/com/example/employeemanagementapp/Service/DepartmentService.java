package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.DepartmentMapper;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Models.DepartmentDisplay;
import com.example.employeemanagementapp.Repositories.DepartmentRepository;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    private static DepartmentRepository departmentRepository;
    private static EmployeeRepository employeeRepository;
    private static PaginationServiceImpl<Departments> paginationService;

    public DepartmentService() throws Exception {
        departmentRepository = (DepartmentRepository) new DepartmentRepository()
                .Mapper(new DepartmentMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("departments").build();

        employeeRepository = (EmployeeRepository) new EmployeeRepository()
                .Mapper(new EmployeeMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("employees").build();

        paginationService = new PaginationServiceImpl<>(departmentRepository);
    }

    public List<Departments> fetchList(int numOfRows, int page) throws Exception {
        return paginationService.fetchData(numOfRows, page);
    }

    public List<DepartmentDisplay> fetchListWithEmployeeName(int numOfRows, int page) throws Exception {
        List<Departments> list = paginationService.fetchData(numOfRows, page);
        List<DepartmentDisplay> displayList = new ArrayList<>();

        list.forEach(departments -> {
            Employee employee = null;
            try {
                employee = employeeRepository.findById(departments.getManager_id(), "employee_id");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            displayList.add(new DepartmentDisplay.Builder()
                            .Department_id(departments.getDepartment_id())
                            .Department_name(departments.getDepartment_name())
                            .Manager(employee.getFirst_name() + employee.getLast_name())
                            .Created_at(departments.getCreated_at())
                            .Updated_at(departments.getUpdated_at())
                            .build()
                    );
        });

        return displayList;
    }
}
