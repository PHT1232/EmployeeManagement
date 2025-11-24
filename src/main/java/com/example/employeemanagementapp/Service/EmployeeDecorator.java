package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Models.Top10EmployeeDisplay;
import com.example.employeemanagementapp.Models.WageDisplay;

public class EmployeeDecorator {
    private DepartmentService departmentService;
    private AttendanceService attendanceService;

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

    public void addDepartmentName(Top10EmployeeDisplay display, int employeeId) {
        Departments departments = departmentService.findDepartmentByEmployeeId(employeeId);
        if (departments == null) {
            display.setDepartment("Empty");
        } else {
            display.setDepartment(departments.getDepartment_name());
        }
    }
}
