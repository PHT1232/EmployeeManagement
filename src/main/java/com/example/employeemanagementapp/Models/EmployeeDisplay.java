package com.example.employeemanagementapp.Models;

import javafx.beans.property.*;

import java.sql.Date;

public class EmployeeDisplay {
    private IntegerProperty employeeId = new SimpleIntegerProperty();
    private StringProperty employeeName = new SimpleStringProperty();
    private StringProperty contact = new SimpleStringProperty();
    private StringProperty role = new SimpleStringProperty();
    private StringProperty hire_date = new SimpleStringProperty();
    private StringProperty position = new SimpleStringProperty();
    private DoubleProperty salary = new SimpleDoubleProperty();
    private ObjectProperty<Date> startDate = new SimpleObjectProperty<>();

    private EmployeeDisplay(Builder builder) {
        employeeId = builder.employeeId;
        employeeName = builder.employeeName;
        contact = builder.contact;
        hire_date = builder.hire_date;
        salary = builder.salary;
        role = builder.role;
        position = builder.position;
        startDate = builder.startDate;
    }

    public static class Builder {
        private IntegerProperty employeeId = new SimpleIntegerProperty();
        private StringProperty employeeName = new SimpleStringProperty();
        private StringProperty contact = new SimpleStringProperty();
        private StringProperty hire_date = new SimpleStringProperty();
        private StringProperty role = new SimpleStringProperty();
        private StringProperty position = new SimpleStringProperty();
        private DoubleProperty salary = new SimpleDoubleProperty();
        private ObjectProperty<Date> startDate = new SimpleObjectProperty<>();

        public Builder EmployeeId(int employeeId) {
            this.employeeId.set(employeeId);
            return this;
        }

        public Builder EmployeeName(String employeeName) {
            this.employeeName.set(employeeName);
            return this;
        }

        public Builder Hire_date(String hire_date) {
            this.hire_date.set(hire_date);
            return this;
        }

        public Builder Salary(double salary) {
            this.salary.set(salary);
            return this;
        }

        public Builder Contact(String contact) {
            this.contact.set(contact);
            return this;
        }

        public Builder Role(String role) {
            this.role.set(role);
            return this;
        }

        public Builder Position(String position) {
            this.position.set(position);
            return this;
        }

        public Builder StartDate(Date startDate) {
            this.startDate.set(startDate);
            return this;
        }

        public EmployeeDisplay build() {
            return new EmployeeDisplay(this);
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

    public double getSalary() {
        return salary.get();
    }

    public DoubleProperty salaryProperty() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }

    public String getHire_date() {
        return hire_date.get();
    }

    public StringProperty hire_dateProperty() {
        return hire_date;
    }

    public void setHire_date(String hire_date) {
        this.hire_date.set(hire_date);
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

    public String getContact() {
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public Date getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<Date> startDateProperty() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate.set(startDate);
    }
}
