package com.example.employeemanagementapp.Models;

public class AttendanceDisplay {
    private int id;
    private String employeeName;
    private String status;
    private String timeAgo;

    public AttendanceDisplay() {
    }

    public AttendanceDisplay(int id, String status, String timeAgo) {
        this.id = id;
        this.status = status;
        this.timeAgo = timeAgo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }
}
