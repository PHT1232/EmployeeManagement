package com.example.employeemanagementapp.Models;

import com.example.employeemanagementapp.Entities.Departments;
import javafx.beans.property.*;

import java.sql.Date;

public class DepartmentDisplay {
    private IntegerProperty department_id = new SimpleIntegerProperty();
    private StringProperty department_name = new SimpleStringProperty();
    private StringProperty manager = new SimpleStringProperty();
    private ObjectProperty<Date> created_at = new SimpleObjectProperty<>();
    private ObjectProperty<Date> updated_at = new SimpleObjectProperty<>();

    private DepartmentDisplay(Builder builder) {
        department_id = builder.department_id;
        department_name = builder.department_name;
        manager = builder.manager;
        created_at = builder.created_at;
        updated_at = builder.updated_at;
    }

    public static class Builder {
        private IntegerProperty department_id = new SimpleIntegerProperty();
        private StringProperty department_name = new SimpleStringProperty();
        private StringProperty manager = new SimpleStringProperty();
        private ObjectProperty<Date> created_at = new SimpleObjectProperty<>();
        private ObjectProperty<Date> updated_at = new SimpleObjectProperty<>();

        public Builder Department_id(int department_id) {
            this.department_id.set(department_id);
            return this;
        }

        public Builder Department_name(String department_name) {
            this.department_name.set(department_name);
            return this;
        }

        public Builder Manager(String manager) {
            this.manager.set(manager);
            return this;
        }

        public Builder Created_at(Date created_at) {
            this.created_at.set(created_at);
            return this;
        }

        public Builder Updated_at(Date updated_at) {
            this.updated_at.set(updated_at);
            return this;
        }

        public DepartmentDisplay build() {
            return new DepartmentDisplay(this);
        }
    }

    public int getDepartment_id() {
        return department_id.get();
    }

    public IntegerProperty department_idProperty() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id.set(department_id);
    }

    public String getDepartment_name() {
        return department_name.get();
    }

    public StringProperty department_nameProperty() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name.set(department_name);
    }

    public String getManager() {
        return manager.get();
    }

    public StringProperty managerProperty() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager.set(manager);
    }

    public Date getCreated_at() {
        return created_at.get();
    }

    public ObjectProperty<Date> created_atProperty() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at.set(created_at);
    }

    public Date getUpdated_at() {
        return updated_at.get();
    }

    public ObjectProperty<Date> updated_atProperty() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at.set(updated_at);
    }
}
