package com.example.employeemanagementapp.Entities;

public class ProjectAssignments {
    private int project_id;
    private int employee_id;
    private String role;

    public ProjectAssignments() {
    }

    public ProjectAssignments(int project_id, int employee_id, String role) {
        this.project_id = project_id;
        this.employee_id = employee_id;
        this.role = role;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
