package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.DepartmentAssignments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Models.DepartmentDisplay;
import com.example.employeemanagementapp.Repositories.DepartmentAssignmentRepository;

import java.util.List;

public class DepartmentDecorator {
    private EmployeeService employeeService;
    private DepartmentAssignmentRepository repository;

    public DepartmentDecorator() throws Exception {
        employeeService = new EmployeeService();
        repository = (DepartmentAssignmentRepository) new DepartmentAssignmentRepository()
                .TableName("department_assignments")
                .build();
    }

    public void addEmployeeName(DepartmentDisplay departmentDisplay, int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        departmentDisplay.setManager(employee.getFirst_name() + " " + employee.getLast_name());
    }

    public void addEmployeeToDepartment(int id, List<Employee> list) {
        list.forEach(employee -> {
            try {
                DepartmentAssignments departmentAssignments = new DepartmentAssignments();
                departmentAssignments.setDepartment_id(id);
                departmentAssignments.setEmployee_id(employee.getEmployee_id());

                repository.insert(departmentAssignments);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public List<Employee> getEmployee(int id) throws Exception {
        return repository.getEmployeeByDepartmentId(id);
    }

    public int deleteEmployeeFromDepartment(DepartmentAssignments departmentAssignments) {
        try {
            return repository.delete(departmentAssignments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
