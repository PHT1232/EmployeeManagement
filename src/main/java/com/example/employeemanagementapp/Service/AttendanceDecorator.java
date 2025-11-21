package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Models.AttendanceDisplay;

public class AttendanceDecorator {
    private static EmployeeService employeeService;

    public AttendanceDecorator() {
        try {
            employeeService = new EmployeeService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmployeeName(AttendanceDisplay attendanceDisplay, int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        attendanceDisplay.setEmployeeName(employee.getFirst_name() + " " + employee.getLast_name());
    }
}
