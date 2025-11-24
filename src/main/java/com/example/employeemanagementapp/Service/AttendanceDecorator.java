package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Models.AttendanceDisplay;
import com.example.employeemanagementapp.Models.RecentActivityDisplay;
import com.example.employeemanagementapp.Repositories.DepartmentAssignmentRepository;

public class AttendanceDecorator {
    private static EmployeeService employeeService;
    private static DepartmentService departmentService;

    public AttendanceDecorator() {
        try {
            employeeService = new EmployeeService();
            departmentService = new DepartmentService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmployeeName(RecentActivityDisplay attendanceDisplay, int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        attendanceDisplay.setEmployeeName(employee.getFirst_name() + " " + employee.getLast_name());
    }

    public void addEmployeeName(AttendanceDisplay attendanceDisplay, int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        attendanceDisplay.setEmployeeName(employee.getFirst_name() + " " + employee.getLast_name());
    }

    public void addDepartmentName(AttendanceDisplay attendanceDisplay, int employeeId) {
        Departments departments = departmentService.findDepartmentByEmployeeId(employeeId);
        attendanceDisplay.setDepartment(departments == null ? "Empty" : departments.getDepartment_name());
    }
}
