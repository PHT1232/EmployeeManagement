package com.example.employeemanagementapp.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class EmployeeDisplay {
    private StringProperty employeeName = new SimpleStringProperty();
    private StringProperty contact = new SimpleStringProperty();
    private StringProperty role = new SimpleStringProperty();
    private StringProperty position = new SimpleStringProperty();
    private ObjectProperty<Date> startDate = new SimpleObjectProperty<>();

    private EmployeeDisplay(Builder builder) {
        employeeName = builder.employeeName;
        contact = builder.contact;
        role = builder.role;
        position = builder.position;
        startDate = builder.startDate;
    }

    public static class Builder {
        private StringProperty employeeName = new SimpleStringProperty();
        private StringProperty contact = new SimpleStringProperty();
        private StringProperty role = new SimpleStringProperty();
        private StringProperty position = new SimpleStringProperty();
        private ObjectProperty<Date> startDate = new SimpleObjectProperty<>();

        public Builder EmployeeName(String employeeName) {
            this.employeeName.set(employeeName);
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
