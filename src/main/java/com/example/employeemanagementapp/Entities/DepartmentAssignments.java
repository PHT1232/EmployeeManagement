package com.example.employeemanagementapp.Entities;

public class DepartmentAssignments {
    private int department_id;
    private int employee_id;

    public DepartmentAssignments() {
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
