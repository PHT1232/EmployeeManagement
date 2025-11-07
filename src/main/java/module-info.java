module com.example.employeemanagementapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;

    opens com.example.employeemanagementapp to javafx.fxml;
    exports com.example.employeemanagementapp;
}