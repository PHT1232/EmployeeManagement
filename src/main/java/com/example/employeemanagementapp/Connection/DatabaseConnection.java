package com.example.employeemanagementapp.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;
    private static final String Url = "jdbc:mysql://localhost:3306/EmployeeManagement";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(Url, user, password);
        }

        return connection;
    }
}
