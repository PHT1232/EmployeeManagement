package com.example.crm.model;

import javafx.beans.property.*;

public class Employee {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty avatarUrl = new SimpleStringProperty();
    private final StringProperty department = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty checkIn = new SimpleStringProperty();
    private final StringProperty checkOut = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final IntegerProperty overtimeHours = new SimpleIntegerProperty();
    private final DoubleProperty bonusAmount = new SimpleDoubleProperty();

    public Employee(int id, String name, String avatarUrl, String department,
                    String status, String checkIn, String checkOut, String location,
                    int overtimeHours, double bonusAmount) {
        this.id.set(id);
        this.name.set(name);
        this.avatarUrl.set(avatarUrl);
        this.department.set(department);
        this.status.set(status);
        this.checkIn.set(checkIn);
        this.checkOut.set(checkOut);
        this.location.set(location);
        this.overtimeHours.set(overtimeHours);
        this.bonusAmount.set(bonusAmount);
    }

    // Getters and property accessors
    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public String getAvatarUrl() { return avatarUrl.get(); }
    public StringProperty avatarUrlProperty() { return avatarUrl; }

    public String getDepartment() { return department.get(); }
    public StringProperty departmentProperty() { return department; }

    public String getStatus() { return status.get(); }
    public StringProperty statusProperty() { return status; }

    public String getCheckIn() { return checkIn.get(); }
    public StringProperty checkInProperty() { return checkIn; }

    public String getCheckOut() { return checkOut.get(); }
    public StringProperty checkOutProperty() { return checkOut; }

    public String getLocation() { return location.get(); }
    public StringProperty locationProperty() { return location; }

    public int getOvertimeHours() { return overtimeHours.get(); }
    public IntegerProperty overtimeHoursProperty() { return overtimeHours; }

    public double getBonusAmount() { return bonusAmount.get(); }
    public DoubleProperty bonusAmountProperty() { return bonusAmount; }
}
