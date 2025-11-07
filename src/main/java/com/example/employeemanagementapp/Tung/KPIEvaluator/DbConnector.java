package com.example.employeemanagementapp.Tung.KPIEvaluator;

import java.sql.*;

public class DbConnector {
    public static final String url = "jdbc:mysql://localhost:3306/e-project-2";
    public static final  String user = "root";
    public static final String password = "";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }
}