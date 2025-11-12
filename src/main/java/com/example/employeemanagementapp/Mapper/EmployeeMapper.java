package com.example.employeemanagementapp.Mapper;

import com.example.employeemanagementapp.Entities.Employee;

import java.sql.ResultSet;

public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet resultSet) throws Exception {
        Employee employee = new Employee.Builder()
                .Employee_id(resultSet.getInt("employee_id"))
                .First_name(resultSet.getString("first_name"))
                .Last_name(resultSet.getString("last_name"))
                .Email(resultSet.getString("email"))
                .Phone(resultSet.getString("phone"))
                .Position(resultSet.getString("position"))
                .Salary(resultSet.getDouble("salary"))
                .Hire_date(resultSet.getDate("hire_date"))
                .Department_id(resultSet.getInt("department_id"))
                .Created_at(resultSet.getDate("created_at"))
                .Updated_at(resultSet.getDate("updated_at"))
                .Total_hours_month(resultSet.getDouble("total_hours_month"))
                .Bonus_hours_month(resultSet.getDouble("bonus_hours_month"))
                .Monthly_salary(resultSet.getDouble("monthly_salary"))
                .Project_bonus_month(resultSet.getDouble("project_bonus_month"))
                .build();

        return employee;
    }
}
