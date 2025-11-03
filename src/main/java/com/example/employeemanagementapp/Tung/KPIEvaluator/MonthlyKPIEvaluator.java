import java.sql.*;
import java.util.*;

public class MonthlyKPIEvaluator {

    static class MonthlyStats {
        int id;
        String name;
        double baseWage;
        double totalHours = 0.0;
        double bonusHours = 0.0;
        int daysQualified = 0; 
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter month start date (YYYY-MM-DD):");
        java.sql.Date monthStart = java.sql.Date.valueOf(sc.nextLine().trim());
        System.out.println("Enter month end date (YYYY-MM-DD):");
        java.sql.Date monthEnd = java.sql.Date.valueOf(sc.nextLine().trim());

        try (Connection conn = DbConnector.getConnection()) {
            Map<Integer, MonthlyStats> statsMap = fetchMonthlyData(conn, monthStart, monthEnd);
            updateMonthlySalaries(conn, statsMap);
            displayTop10(conn);
        }
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

    private static double round(double v, int places) {
        double factor = Math.pow(10, places);
        return Math.round(v * factor) / factor;
    }

}