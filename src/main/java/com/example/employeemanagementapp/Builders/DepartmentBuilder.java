package com.example.employeemanagementapp.Builders;

import com.example.employeemanagementapp.Entities.Departments;

import java.sql.Date;

public class DepartmentBuilder extends Builder<Departments> {
    private int department_id;
    private String department_name;
    private int manager_id;
    private Date created_at;
    private Date updated_at;

    public DepartmentBuilder Department_id(int department_id) {
        this.department_id = department_id;
        return this;
    }

    public DepartmentBuilder Department_name(String department_name) {
        this.department_name = department_name;
        return this;
    }

    public DepartmentBuilder Manager_id(int manager_id) {
        this.manager_id = manager_id;
        return this;
    }

    public DepartmentBuilder Created_at(Date created_at) {
        this.created_at = created_at;
        return this;
    }

    public DepartmentBuilder Updated_at(Date updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    @Override
    public Departments build() {
        Departments departments = new Departments();
        departments.setDepartment_id(department_id);
        departments.setDepartment_name(department_name);
        departments.setManager_id(manager_id);
        departments.setCreated_at(created_at);
        departments.setUpdated_at(updated_at);

        return departments;
    }
}
