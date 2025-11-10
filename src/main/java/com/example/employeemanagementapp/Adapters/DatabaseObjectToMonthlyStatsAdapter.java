package com.example.employeemanagementapp.Adapters;

import com.example.employeemanagementapp.Models.MonthlyStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseObjectToMonthlyStatsAdapter {
    public List<MonthlyStats> convert(ResultSet rs) throws SQLException {
        List<MonthlyStats> monthlyStatsList = new ArrayList<>();
        while (rs.next()) {
            int employeeId = rs.getInt("employee_id");
            MonthlyStats ms = new MonthlyStats();
            ms.setEmployee_id(employeeId);
            ms.setId(employeeId);
            ms.setName(rs.getString("name"));
            ms.setBaseWage(rs.getDouble("base_wage"));

            Timestamp checkIn = rs.getTimestamp("check_in");
            Timestamp checkOut = rs.getTimestamp("check_out");

            if (checkIn != null && checkOut != null) {
                double hours = (checkOut.getTime() - checkIn.getTime()) / (1000.0 * 60 * 60);

                if (hours >= 6.0) {
                    ms.increaseDaysQualified(1);
                    ms.setTotalHours(hours);
                    if (hours >= 7.0) {
                        ms.increaseBonusHours(hours - 6.0);
                    }
                }
            }
            monthlyStatsList.add(ms);
        }

        return monthlyStatsList;
    }
}
