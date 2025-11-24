package com.example.employeemanagementapp.Models;

import javafx.beans.property.*;

import java.sql.Date;
import java.sql.Timestamp;

public class AttendanceDisplay {
    private IntegerProperty attendanceId = new SimpleIntegerProperty();
    private StringProperty employeeName = new SimpleStringProperty();
    private StringProperty department = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();
    private StringProperty checkIn = new SimpleStringProperty();
    private StringProperty checkOut = new SimpleStringProperty();

    private AttendanceDisplay(Builder builder) {
        attendanceId = builder.attendanceId;
        employeeName = builder.employeeName;
        department = builder.department;
        status = builder.status;
        checkIn = builder.checkIn;
        checkOut = builder.checkOut;
    }

    public static class Builder {
        private IntegerProperty attendanceId = new SimpleIntegerProperty();
        private StringProperty employeeName = new SimpleStringProperty();
        private StringProperty department = new SimpleStringProperty();
        private StringProperty status = new SimpleStringProperty();
        private StringProperty checkIn = new SimpleStringProperty();
        private StringProperty checkOut = new SimpleStringProperty();

        public Builder AttendanceId(int attendanceId) {
            this.attendanceId.set(attendanceId);
            return this;
        }

        public Builder EmployeeName(String employeeName) {
            this.employeeName.set(employeeName);
            return this;
        }

        public Builder Department(String department) {
            this.department.set(department);
            return this;
        }

        public Builder Status(String status) {
            this.status.set(status);
            return this;
        }

        public Builder CheckIn(String checkIn) {
            this.checkIn.set(checkIn);
            return this;
        }

        public Builder CheckOut(String checkOut) {
            this.checkOut.set(checkOut);
            return this;
        }

        public AttendanceDisplay build() {
            return new AttendanceDisplay(this);
        }
    }

    public int getAttendanceId() {
        return attendanceId.get();
    }

    public IntegerProperty attendanceIdProperty() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId.set(attendanceId);
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public StringProperty employeeNameProperty() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName.set(employeeName);
    }

    public String getDepartment() {
        return department.get();
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getCheckIn() {
        return checkIn.get();
    }

    public StringProperty checkInProperty() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn.set(checkIn);
    }

    public String getCheckOut() {
        return checkOut.get();
    }

    public StringProperty checkOutProperty() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut.set(checkOut);
    }
}
