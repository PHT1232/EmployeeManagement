package com.example.employeemanagementapp.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Top10EmployeeDisplay {
    private IntegerProperty employeeId = new SimpleIntegerProperty();
    private StringProperty employeeName = new SimpleStringProperty();
    private StringProperty department = new SimpleStringProperty();
    private StringProperty overtimeHours = new SimpleStringProperty();
    private StringProperty bonusAmount = new SimpleStringProperty();

    private Top10EmployeeDisplay(Builder builder) {
        this.employeeId = builder.employeeId;
        this.employeeName = builder.employeeName;
        this.department = builder.department;
        this.overtimeHours = builder.overtimeHours;
        this.bonusAmount = builder.bonusAmount;
    }

    // Static Builder class
    public static class Builder {
        private IntegerProperty employeeId = new SimpleIntegerProperty();
        private StringProperty employeeName = new SimpleStringProperty();
        private StringProperty department = new SimpleStringProperty();
        private StringProperty overtimeHours = new SimpleStringProperty();
        private StringProperty bonusAmount = new SimpleStringProperty();

        public Builder EmployeeId(int employeeId) {
            this.employeeId.set(employeeId);
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

        public Builder OvertimeHours(String overtimeHours) {
            this.overtimeHours.set(overtimeHours);
            return this;
        }

        public Builder BonusAmount(String bonusAmount) {
            this.bonusAmount.set(bonusAmount);
            return this;
        }

        public Top10EmployeeDisplay build() {
            return new Top10EmployeeDisplay(this);
        }
    }

    public int getEmployeeId() {
        return employeeId.get();
    }

    public IntegerProperty employeeIdProperty() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId.set(employeeId);
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

    public String getOvertimeHours() {
        return overtimeHours.get();
    }

    public StringProperty overtimeHoursProperty() {
        return overtimeHours;
    }

    public void setOvertimeHours(String overtimeHours) {
        this.overtimeHours.set(overtimeHours);
    }

    public String getBonusAmount() {
        return bonusAmount.get();
    }

    public StringProperty bonusAmountProperty() {
        return bonusAmount;
    }

    public void setBonusAmount(String bonusAmount) {
        this.bonusAmount.set(bonusAmount);
    }
}
