package com.example.employeemanagementapp.Entities;

import java.sql.Date;

public class Employee {
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

    public Employee(){}

    public double getTotal_hours_month() {
        return total_hours_month;
    }

    public void setTotal_hours_month(double total_hours_month) {
        this.total_hours_month = total_hours_month;
    }

    public double getBonus_hours_month() {
        return bonus_hours_month;
    }

    public void setBonus_hours_month(double bonus_hours_month) {
        this.bonus_hours_month = bonus_hours_month;
    }

    public double getMonthly_salary() {
        return monthly_salary;
    }

    public void setMonthly_salary(double monthly_salary) {
        this.monthly_salary = monthly_salary;
    }

    public double getProject_bonus_month() {
        return project_bonus_month;
    }

    public void setProject_bonus_month(double project_bonus_month) {
        this.project_bonus_month = project_bonus_month;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id=" + employee_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hire_date=" + hire_date +
                ", department_id=" + department_id +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
