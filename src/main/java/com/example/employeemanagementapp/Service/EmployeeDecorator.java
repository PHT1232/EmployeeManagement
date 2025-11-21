package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Models.WageDisplay;

public class EmployeeDecorator {
    public DepartmentService departmentService;

    public EmployeeDecorator() {
        try {
            departmentService = new DepartmentService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addDepartmentName(WageDisplay wageDisplay, int employeeId) {
        Departments departments = departmentService.findDepartmentByEmployeeId(employeeId);
        if (departments == null) {
            wageDisplay.setDepartment("Empty");
        } else {
            wageDisplay.setDepartment(departments.getDepartment_name());
        }
    }
}
