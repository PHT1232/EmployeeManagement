package com.example.employeemanagementapp.Mapper;

import com.example.employeemanagementapp.Builders.EmployeeBuilder;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Factories.EntityFactory;
import com.example.employeemanagementapp.Factories.EntityFactoryImpl;

import java.sql.ResultSet;

public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet resultSet) throws Exception {
        EntityFactory entityFactoryProvider = new EntityFactoryImpl();
        Employee employee = entityFactoryProvider.getBuilder(EmployeeBuilder.class, new EmployeeBuilder())
                .Employee_id(resultSet.getInt("employee_id"))
                .First_name(resultSet.getString("first_name"))
                .Last_name(resultSet.getString("last_name"))
                .Email(resultSet.getString("email"))
                .Phone(resultSet.getString("phone"))
                .Position(resultSet.getString("position"))
                .Salary(resultSet.getDouble("salary"))
                .Hire_date(resultSet.getDate("hire_date"))
                .Department_id(resultSet.getInt("department_id"))
                .Manager_id(resultSet.getInt("manager_id"))
                .Created_at(resultSet.getDate("created_at"))
                .Updated_at(resultSet.getDate("updated_at"))
                .build();

        return employee;
    }
}
