package com.example.employeemanagementapp.Entities;

import java.sql.Date;
import java.sql.Timestamp;

public class Attendance {
    private int attendance_id;
    private int employee_id;
    private Date attendance_date;
    private Timestamp check_in;
    private Timestamp check_out;
    private String status;
    private String notes;
    private Timestamp created_at;
    private Timestamp updated_at;
    
    private Attendance(Builder builder) {
        attendance_id = builder.attendance_id;
        employee_id = builder.employee_id;
        attendance_date = builder.attendance_date;
        check_in = builder.check_in;
        check_out = builder.check_out;
        status = builder.status;
        notes = builder.notes;
        created_at = builder.created_at;
        updated_at = builder.updated_at;
    }

    public static class Builder {
        private int attendance_id;
        private int employee_id;
        private Date attendance_date;
        private Timestamp check_in;
        private Timestamp check_out;
        private String status;
        private String notes;
        private Timestamp created_at;
        private Timestamp updated_at;

        public Builder Attendance_id(int attendance_id) {
            this.attendance_id = attendance_id;
            return this;
        }

        public Builder Employee_id(int employee_id) {
            this.employee_id = employee_id;
            return this;
        }

        public Builder Attendance_date(Date attendance_date) {
            this.attendance_date = attendance_date;
            return this;
        }

        public Builder Check_in(Timestamp check_in) {
            this.check_in = check_in;
            return this;
        }

        public Builder Check_out(Timestamp check_out) {
            this.check_out = check_out;
            return this;
        }

        public Builder Status(String status) {
            this.status = status;
            return this;
        }

        public Builder Notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder Created_at(Timestamp created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder Updated_at(Timestamp updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public Attendance build() {
            return new Attendance(this);
        }
    }

    public Attendance() {
    }

    public int getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(int attendance_id) {
        this.attendance_id = attendance_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public Date getAttendance_date() {
        return attendance_date;
    }

    public void setAttendance_date(Date attendance_date) {
        this.attendance_date = attendance_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCheck_in() {
        return check_in;
    }

    public void setCheck_in(Timestamp check_in) {
        this.check_in = check_in;
    }

    public Timestamp getCheck_out() {
        return check_out;
    }

    public void setCheck_out(Timestamp check_out) {
        this.check_out = check_out;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
