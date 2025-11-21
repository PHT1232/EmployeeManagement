package com.example.employeemanagementapp.Models;

import javafx.beans.property.*;

import java.sql.Date;

public class ProjectDisplay {
    private IntegerProperty projectId = new SimpleIntegerProperty();
    private StringProperty projectName = new SimpleStringProperty();
    private ObjectProperty<Date> startDate = new SimpleObjectProperty<>();
    private ObjectProperty<Date> endDate = new SimpleObjectProperty<>();
    private DoubleProperty commissionRate = new SimpleDoubleProperty();
    private StringProperty numberOfEmployee = new SimpleStringProperty();
    private DoubleProperty revenue = new SimpleDoubleProperty();
    private DoubleProperty totalEarnings = new SimpleDoubleProperty();
    private StringProperty status = new SimpleStringProperty();

    public ProjectDisplay(Builder builder) {
        projectId = builder.projectId;
        projectName = builder.projectName;
        startDate = builder.startDate;
        endDate = builder.endDate;
        commissionRate = builder.commissionRate;
        numberOfEmployee = builder.numberOfEmployee;
        revenue = builder.revenue;
        totalEarnings = builder.totalEarnings;
        status = builder.status;
    }

    public static class Builder {
        private IntegerProperty projectId = new SimpleIntegerProperty();
        private StringProperty projectName = new SimpleStringProperty();
        private ObjectProperty<Date> startDate = new SimpleObjectProperty<>();
        private ObjectProperty<Date> endDate = new SimpleObjectProperty<>();
        private DoubleProperty commissionRate = new SimpleDoubleProperty();
        private StringProperty numberOfEmployee = new SimpleStringProperty();
        private DoubleProperty revenue = new SimpleDoubleProperty();
        private DoubleProperty totalEarnings = new SimpleDoubleProperty();
        private StringProperty status = new SimpleStringProperty();

        public Builder ProjectId(int id) {
            this.projectId.set(id);
            return this;
        }

        public Builder ProjectName(String projectName) {
            this.projectName.set(projectName);
            return this;
        }

        public Builder StartDate(Date startDate) {
            this.startDate.set(startDate);
            return this;
        }

        public Builder EndDate(Date endDate) {
            this.endDate.set(endDate);
            return this;
        }

        public Builder CommissionRate(double commissionRate) {
            this.commissionRate.set(commissionRate);
            return this;
        }

        public Builder NumberOfEmployee(String numberOfEmployee) {
            this.numberOfEmployee.set(numberOfEmployee);
            return this;
        }

        public Builder Revenue(double revenue) {
            this.revenue.set(revenue);
            return this;
        }

        public Builder TotalEarnings(double totalEarnings) {
            this.totalEarnings.set(totalEarnings);
            return this;
        }

        public Builder Status(String status) {
            this.status.set(status);
            return this;
        }

        public ProjectDisplay build() {
            return new ProjectDisplay(this);
        }
    }

    public int getProjectId() {
        return projectId.get();
    }

    public IntegerProperty projectIdProperty() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId.set(projectId);
    }

    public String getProjectName() {
        return projectName.get();
    }

    public StringProperty projectNameProperty() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName.set(projectName);
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

    public Date getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<Date> endDateProperty() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate.set(endDate);
    }

    public double commissionRateProperty() {
        return commissionRate.get();
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate.set(commissionRate);
    }

    public String getNumberOfEmployee() {
        return numberOfEmployee.get();
    }

    public StringProperty numberOfEmployeeProperty() {
        return numberOfEmployee;
    }

    public void setNumberOfEmployee(String numberOfEmployee) {
        this.numberOfEmployee.set(numberOfEmployee);
    }

    public double getRevenue() {
        return revenue.get();
    }

    public DoubleProperty revenueProperty() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue.set(revenue);
    }

    public double getTotalEarnings() {
        return totalEarnings.get();
    }

    public DoubleProperty totalEarningsProperty() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings.set(totalEarnings);
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
}
