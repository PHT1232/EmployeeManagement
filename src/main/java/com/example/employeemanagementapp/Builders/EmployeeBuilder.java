package com.example.employeemanagementapp.Builders;

import com.example.employeemanagementapp.Entities.Employee;

import java.sql.Date;

public class EmployeeBuilder extends Builder<Employee> {
    private int employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String position;
    private double salary;
    private Date hire_date;
    private int department_id;
    private Date created_at;
    private Date updated_at;
    private double total_hours_month;
    private double bonus_hours_month;
    private double monthly_salary;
    private double project_bonus_month;

    public EmployeeBuilder Employee_id(int employee_id) {
        this.employee_id = employee_id;
        return this;
    }

    public EmployeeBuilder First_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public EmployeeBuilder Last_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public EmployeeBuilder Email(String email) {
        this.email = email;
        return this;
    }

    public EmployeeBuilder Phone(String phone) {
        this.phone = phone;
        return this;
    }

    public EmployeeBuilder Position(String position) {
        this.position = position;
        return this;
    }

    public EmployeeBuilder Salary(double salary) {
        this.salary = salary;
        return this;
    }

    public EmployeeBuilder Hire_date(Date hire_date) {
        this.hire_date = hire_date;
        return this;
    }

    public EmployeeBuilder Department_id(int department_id) {
        this.department_id = department_id;
        return this;
    }

    public EmployeeBuilder Created_at(Date created_at) {
        this.created_at = created_at;
        return this;
    }

    public EmployeeBuilder Updated_at(Date updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    public EmployeeBuilder Total_hours_month(double total_hours_month) {
        this.total_hours_month = total_hours_month;
        return this;
    }

    public EmployeeBuilder Bonus_hours_month(double bonus_hours_month) {
        this.bonus_hours_month = bonus_hours_month;
        return this;
    }

    public EmployeeBuilder Monthly_salary(double monthly_salary) {
        this.monthly_salary = monthly_salary;
        return this;
    }

    public EmployeeBuilder Project_bonus_month(double project_bonus_month) {
        this.project_bonus_month = project_bonus_month;
        return this;
    }

    @Override
    public Employee build() {
        Employee employee = new Employee();
        employee.setEmployee_id(employee_id);
        employee.setFirst_name(first_name);
        employee.setLast_name(last_name);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setPosition(position);
        employee.setSalary(salary);
        employee.setHire_date(hire_date);
        employee.setDepartment_id(department_id);
        employee.setCreated_at(created_at);
        employee.setUpdated_at(updated_at);
        employee.setTotal_hours_month(total_hours_month);
        employee.setBonus_hours_month(bonus_hours_month);
        employee.setMonthly_salary(monthly_salary);
        employee.setProject_bonus_month(project_bonus_month);

        return employee;
    }
}
