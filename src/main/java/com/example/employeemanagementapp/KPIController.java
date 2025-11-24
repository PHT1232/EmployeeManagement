package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Models.WageDisplay;
import com.example.employeemanagementapp.Service.AttendanceService;
import com.example.employeemanagementapp.Service.EmployeeService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KPIController {

    @FXML
    private Label main_header_label;

    @FXML
    private Label main_date_label;

    @FXML
    private Label first_card_label;

    @FXML
    private Label active_label;

    @FXML
    private Label second_card_label;

    @FXML
    private Label current_present_label;

    @FXML
    private Label third_card_label;

    @FXML
    private Label left_for_today_label;

    @FXML
    private Label fouth_card_label;

    @FXML
    private Label today_attendance_label;

    @FXML
    private ScrollPane main_scrollpane;

    @FXML
    private Label project_overview_label;

    @FXML
    private Label first_num_label;

    @FXML
    private Label second_num_label;

    @FXML
    private Label third_num_label;

    @FXML
    private Label forth_num_label;

    @FXML
    private Button add_project_button;

    @FXML
    private TableView<WageDisplay> project_table;

    @FXML
    private Button first_prev_button;

    @FXML
    private Button first_next_button;

    static class MonthlyStats {
        int id;
        String name;
        double baseWage;
        double totalHours = 0.0;
        double bonusHours = 0.0;
        int daysQualified = 0;
    }

    private static Map<Integer, MonthlyStats> fetchMonthlyData(Connection conn, java.sql.Date monthStart,
                                                               java.sql.Date monthEnd) throws SQLException {
        Map<Integer, MonthlyStats> statsMap = new HashMap<>();

        String sql = "SELECT e.employee_id, " +
                "CONCAT(e.first_name, ' ', e.last_name) AS name, " +
                "e.salary AS base_wage, " +
                "a.check_in, a.check_out, a.attendance_date " +
                "FROM employees e " +
                "JOIN attendance a ON e.employee_id = a.employee_id " +
                "WHERE a.attendance_date BETWEEN ? AND ? " +
                "ORDER BY e.employee_id, a.attendance_date";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, monthStart);
            ps.setDate(2, monthEnd);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("employee_id");
                    MonthlyStats ms = statsMap.getOrDefault(id, new MonthlyStats());
                    ms.id = id;
                    ms.name = rs.getString("name");
                    ms.baseWage = rs.getDouble("base_wage");

                    Timestamp checkIn = rs.getTimestamp("check_in");
                    Timestamp checkOut = rs.getTimestamp("check_out");

                    if (checkIn != null && checkOut != null) {
                        double hours = (checkOut.getTime() - checkIn.getTime()) / (1000.0 * 60 * 60);

                        if (hours >= 6.0) {
                            ms.daysQualified++;
                            ms.totalHours += hours;
                            if (hours >= 7.0) {
                                ms.bonusHours += (hours - 6.0);
                            }
                        }
                    }
                    statsMap.put(id, ms);
                }
            }
        }
        return statsMap;
    }

    private static void updateMonthlySalaries(Connection conn, Map<Integer, MonthlyStats> statsMap)
            throws SQLException {

        String updateSql = "UPDATE employees " +
                "SET total_hours_month = ?, " +
                "bonus_hours_month = ?, " +
                "monthly_salary = ? " +
                "WHERE employee_id = ?";

        try (PreparedStatement ups = conn.prepareStatement(updateSql)) {
            for (MonthlyStats ms : statsMap.values()) {
                double basePay = ms.baseWage * ms.daysQualified;
                double bonus = ms.bonusHours * (ms.baseWage * 0.15);
                double totalSalary = basePay + bonus;

                String getBonusSql = "SELECT project_bonus_month FROM Employees WHERE employee_id = ?";
                try (PreparedStatement bps = conn.prepareStatement(getBonusSql)) {
                    bps.setInt(1, ms.id);
                    try (ResultSet brs = bps.executeQuery()) {
                        if (brs.next()) {
                            double projectBonus = brs.getDouble("project_bonus_month");
                            totalSalary += projectBonus;
                        }
                    }
                }

                ups.setDouble(1, round(ms.totalHours, 2));
                ups.setDouble(2, round(ms.bonusHours, 2));
                ups.setDouble(3, round(totalSalary, 2));
                ups.setInt(4, ms.id);
                ups.addBatch();
            }
            ups.executeBatch();
        }
    }

    private static void displayTop10(Connection conn) throws SQLException {
        String topSql = "SELECT employee_id, CONCAT(first_name, ' ', last_name) AS name, bonus_hours_month " +
                "FROM employees " +
                "ORDER BY bonus_hours_month DESC " +
                "LIMIT 10";

        try (PreparedStatement ps = conn.prepareStatement(topSql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n🏆 Top 10 Employees by Bonus Hours:");
            int rank = 1;
            while (rs.next()) {
                System.out.printf("%2d. %-20s (ID: %d) - %.2f bonus hours%n",
                        rank++,
                        rs.getString("name"),
                        rs.getInt("employee_id"),
                        rs.getDouble("bonus_hours_month"));
            }
        }
    }

    private static void updateProjectBonuses(Connection conn) throws SQLException {
        String sql = "SELECT p.project_id, p.total_revenue, p.commission_rate, COUNT(pa.employee_id) AS team_size " +
                "FROM Projects p " +
                "JOIN ProjectAssignments pa ON p.project_id = pa.project_id " +
                "WHERE p.total_revenue > 0 " + // only completed projects
                "GROUP BY p.project_id";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                double revenue = rs.getDouble("total_revenue");
                double commissionRate = rs.getDouble("commission_rate");
                int teamSize = rs.getInt("team_size");

                double projectBonusEach = (revenue * commissionRate) / teamSize;

                // update each team member
                String updateSql = "UPDATE Employees e " +
                        "JOIN ProjectAssignments pa ON e.employee_id = pa.employee_id " +
                        "SET e.project_bonus_month = e.project_bonus_month + ? " +
                        "WHERE pa.project_id = ?";

                try (PreparedStatement ups = conn.prepareStatement(updateSql)) {
                    ups.setDouble(1, projectBonusEach);
                    ups.setInt(2, projectId);
                    ups.executeUpdate();
                }
            }
        }
    }

    private static double round(double v, int places) {
        double factor = Math.pow(10, places);
        return Math.round(v * factor) / factor;
    }

    @FXML
    protected void calculateKPI() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter month start date (YYYY-MM-DD):");
        java.sql.Date monthStart = java.sql.Date.valueOf(sc.nextLine().trim());
        System.out.println("Enter month end date (YYYY-MM-DD):");
        java.sql.Date monthEnd = java.sql.Date.valueOf(sc.nextLine().trim());

        try (Connection conn = DatabaseConnection.getConnection()) {
            Map<Integer, MonthlyStats> statsMap = fetchMonthlyData(conn, monthStart, monthEnd);
            updateProjectBonuses(conn);
            updateMonthlySalaries(conn, statsMap);
            displayTop10(conn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private EmployeeService employeeService;
    private AttendanceService attendanceService;

    @FXML
    protected void initialize() {
        main_scrollpane.setFitToWidth(true);
        try {
            employeeService = new EmployeeService();
            attendanceService = new AttendanceService();

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            first_num_label.setText(formatter.format(employeeService.getTotalCompensation()));;
            second_num_label.setText(formatter.format(employeeService.getTotalBonus()));
            forth_num_label.setText(String.valueOf(attendanceService.getTotalOvertime()) + "h");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        populateColumn();
                        populateTable();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void populateColumn() {
        TableColumn<WageDisplay, String> employeeNameColumn = new TableColumn<>("Employee");
        TableColumn<WageDisplay, String> positionColumn = new TableColumn<>("Position");
        TableColumn<WageDisplay, String> departmentColumn = new TableColumn<>("Department");
        TableColumn<WageDisplay, Double> baseSalaryColumn = new TableColumn<>("Base Salary");
        TableColumn<WageDisplay, Double> bonusColumn = new TableColumn<>("Bonus");
        TableColumn<WageDisplay, Double> totalCompensationColumn = new TableColumn<>("Total Compensation");

        employeeNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmployeeName()));
        positionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPosition()));
        departmentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDepartment()));
        baseSalaryColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getBaseSalary()).asObject());
        bonusColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getBonus()).asObject());
        totalCompensationColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getTotalCompensation()).asObject());

        project_table.getColumns().addAll(
                employeeNameColumn,
                positionColumn,
                departmentColumn,
                baseSalaryColumn,
                bonusColumn,
                totalCompensationColumn
        );

        project_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void populateTable() throws Exception {
        ObservableList<WageDisplay> list = FXCollections.observableArrayList(employeeService.fetchEmployeeForWageDisplay(10, currentPage));
        project_table.setItems(list);
    }

    private int currentPage = 1;

    @FXML
    protected void prev() {
        currentPage--;
        try {
            if (currentPage != 0) {
                populateTable();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void next() {
        currentPage++;
        try {
            populateTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
