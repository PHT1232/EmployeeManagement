package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Models.DepartmentDisplay;

public class DepartmentDecorator {
    private EmployeeService employeeService;

    public DepartmentDecorator() throws Exception {
        employeeService = new EmployeeService();
    }

    public void addEmployeeName(DepartmentDisplay departmentDisplay, int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        departmentDisplay.setManager(employee.getFirst_name() + " " + employee.getLast_name());
    }
}
