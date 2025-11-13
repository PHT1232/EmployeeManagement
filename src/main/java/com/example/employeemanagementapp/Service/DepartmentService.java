package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Mapper.DepartmentMapper;
import com.example.employeemanagementapp.Repositories.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    private static DepartmentRepository departmentRepository;
    private static PaginationServiceImpl<Departments> paginationService;

    public DepartmentService() throws Exception {
        departmentRepository = (DepartmentRepository) new DepartmentRepository()
                .Mapper(new DepartmentMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("Departments").build();

        paginationService = new PaginationServiceImpl<>(departmentRepository);
    }

    public List<Departments> fetchList(int numOfRows, int page) throws Exception {
        return paginationService.fetchData(numOfRows, page);
    }
}
