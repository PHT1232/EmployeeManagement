package com.example.employeemanagementapp.Models;

public class MonthlyStats {
    private int id;
    private String name;
    private double baseWage;
    private double totalHours = 0.0;
    private double bonusHours = 0.0;
    private int daysQualified = 0;

    public MonthlyStats() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBaseWage() {
        return baseWage;
    }

    public void setBaseWage(double baseWage) {
        this.baseWage = baseWage;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public void increaseTotalHours(double hours) {
        this.totalHours += hours;
    }

    public double getBonusHours() {
        return bonusHours;
    }

    public void increaseBonusHours(double bonusHours) {
        this.bonusHours += bonusHours;
    }

    public void setBonusHours(double bonusHours) {
        this.bonusHours = bonusHours;
    }

    public int getDaysQualified() {
        return daysQualified;
    }

    public void increaseDaysQualified(int number) {
        this.daysQualified += number;
    }

    public void setDaysQualified(int daysQualified) {
        this.daysQualified = daysQualified;
    }
}
