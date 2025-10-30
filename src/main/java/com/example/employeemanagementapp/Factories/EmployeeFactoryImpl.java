package com.example.employeemanagementapp.Factories;

import com.example.employeemanagementapp.Builders.EmployeeBuilder;

public class EmployeeFactoryImpl implements EntityFactory {

    @Override
    public EmployeeBuilder getBuilder() {
        return new EmployeeBuilder();
    }
}
