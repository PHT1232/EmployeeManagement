package com.example.employeemanagementapp.Models;

import javafx.beans.property.*;

public class WageDisplay {
    private IntegerProperty employeeId = new SimpleIntegerProperty();
    private StringProperty employeeName = new SimpleStringProperty();
    private StringProperty position = new SimpleStringProperty();
    private StringProperty department = new SimpleStringProperty();
    private DoubleProperty baseSalary = new SimpleDoubleProperty();
    private DoubleProperty bonus = new SimpleDoubleProperty();
    private DoubleProperty totalCompensation = new SimpleDoubleProperty();

    private WageDisplay(Builder builder) {
        employeeId = builder.employeeId;
        employeeName = builder.employeeName;
        position = builder.position;
        department = builder.department;
        baseSalary = builder.baseSalary;
        bonus = builder.bonus;
        totalCompensation = builder.totalCompensation;
    }

    public static class Builder {
        private IntegerProperty employeeId = new SimpleIntegerProperty();
        private StringProperty employeeName = new SimpleStringProperty();
        private StringProperty position = new SimpleStringProperty();
        private StringProperty department = new SimpleStringProperty();
        private DoubleProperty baseSalary = new SimpleDoubleProperty();
        private DoubleProperty bonus = new SimpleDoubleProperty();
        private DoubleProperty totalCompensation = new SimpleDoubleProperty();

        public Builder EmployeeId(int id) {
            this.employeeId.set(id);
            return this;
        }

        public Builder EmployeeName(String employeeName) {
            this.employeeName.set(employeeName);
            return this;
        }

        public Builder Position(String position) {
            this.position.set(position);
            return this;
        }

        public Builder Department(String department) {
            this.department.set(department);
            return this;
        }

        public Builder BaseSalary(double baseSalary) {
            this.baseSalary.set(baseSalary);
            return this;
        }

        public Builder Bonus(double bonus) {
            this.bonus.set(bonus);
            return this;
        }

        public Builder TotalCompensation(double totalCompensation) {
            this.totalCompensation.set(totalCompensation);
            return this;
        }

        public WageDisplay build() {
            return new WageDisplay(this);
        }
    }

    // Getters, setters, and property methods
    public String getEmployeeName() { return employeeName.get(); }
    public StringProperty employeeNameProperty() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName.set(employeeName); }

    public String getPosition() { return position.get(); }
    public StringProperty positionProperty() { return position; }
    public void setPosition(String position) { this.position.set(position); }

    public String getDepartment() { return department.get(); }
    public StringProperty departmentProperty() { return department; }
    public void setDepartment(String department) { this.department.set(department); }

    public double getBaseSalary() { return baseSalary.get(); }
    public DoubleProperty baseSalaryProperty() { return baseSalary; }
    public void setBaseSalary(double baseSalary) { this.baseSalary.set(baseSalary); }

    public double getBonus() { return bonus.get(); }
    public DoubleProperty bonusProperty() { return bonus; }
    public void setBonus(double bonus) { this.bonus.set(bonus); }

    public double getTotalCompensation() { return totalCompensation.get(); }
    public DoubleProperty totalCompensationProperty() { return totalCompensation; }
    public void setTotalCompensation(double totalCompensation) { this.totalCompensation.set(totalCompensation); }
}
