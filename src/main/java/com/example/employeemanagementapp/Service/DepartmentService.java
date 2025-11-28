package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.DepartmentAssignments;
import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.DepartmentMapper;
import com.example.employeemanagementapp.Models.DepartmentDisplay;
import com.example.employeemanagementapp.Repositories.DepartmentRepository;

import java.sql.Statement;
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

    public Departments findById(int id) {
        Departments departments = null;
        try {
            departments = departmentRepository.findById(id, "department_id");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return departments;
    }

    public int insert(Departments departments) throws Exception {
        return departmentRepository.insert(departments);
    }

    public Departments findDepartmentByEmployeeId(int id) {
        Departments departments = null;
        try {
            departments = departmentRepository.findDepartmentByEmployeeId(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return departments;
    }

    public void addEmployeeToDepartment(int id, List<Employee> employeeId) {
        DepartmentDecorator departmentDecorator = null;
        try {
            departmentDecorator = new DepartmentDecorator();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        departmentDecorator.addEmployeeToDepartment(id, employeeId);
    }

    public int getTotalDepartment() {
        int total = 0;
        String sql = "SELECT COUNT(1) as num FROM departments";

        try {
            total = departmentRepository.getNumberBySql(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return total;
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
                        .Manager_id(departments.getManager_id())
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

    public List<Employee> getEmployee(int id) {
        DepartmentDecorator departmentDecorator = null;
        try {
            departmentDecorator = new DepartmentDecorator();

            return departmentDecorator.getEmployee(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int update(Departments departments) {
        try {
            return departmentRepository.update(departments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteEmployeeFromDepartment(int departmentId, int employeeId) {
        DepartmentDecorator departmentDecorator = null;
        try {
            departmentDecorator = new DepartmentDecorator();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return departmentDecorator.deleteEmployeeFromDepartment(new DepartmentAssignments(departmentId, employeeId));
    }
}
