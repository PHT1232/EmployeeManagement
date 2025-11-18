package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Mapper.DepartmentMapper;
import com.example.employeemanagementapp.Models.DepartmentDisplay;
import com.example.employeemanagementapp.Repositories.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    private static DepartmentRepository departmentRepository;
    private static PaginationServiceImpl<Departments> paginationService;

    public DepartmentService() throws Exception {
        departmentRepository = (DepartmentRepository) new DepartmentRepository()
                .Mapper(new DepartmentMapper())
                .TableName("departments").build();

        paginationService = new PaginationServiceImpl<>(departmentRepository);
    }

    public List<Departments> fetchList(int numOfRows, int page) throws Exception {
        return paginationService.fetchData(numOfRows, page);
    }

    public List<DepartmentDisplay> fetchListWithEmployeeName(int numOfRows, int page) throws Exception {
        List<Departments> list = paginationService.fetchData(numOfRows, page);
        List<DepartmentDisplay> displayList = new ArrayList<>();

        list.forEach(departments -> {
            try {
                DepartmentDecorator departmentDecorator = new DepartmentDecorator();
                DepartmentDisplay departmentDisplay = new DepartmentDisplay.Builder()
                        .Department_id(departments.getDepartment_id())
                        .Department_name(departments.getDepartment_name())
                        .Created_at(departments.getCreated_at())
                        .Updated_at(departments.getUpdated_at())
                        .build();

                departmentDecorator.addEmployeeName(departmentDisplay, departments.getManager_id());

                displayList.add(departmentDisplay);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return displayList;
    }
}
