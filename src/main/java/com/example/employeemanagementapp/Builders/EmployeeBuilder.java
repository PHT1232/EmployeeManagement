package com.example.employeemanagementapp.Builders;

import com.example.employeemanagementapp.Entities.Employee;

public class EmployeeBuilder extends Builder<Employee> {

    @Override
    public Employee build() {
        Employee employee = new Employee();
        return employee;
    }
}
