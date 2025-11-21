package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Attendance;
import com.example.employeemanagementapp.Mapper.AttendanceMapper;
import com.example.employeemanagementapp.Models.AttendanceDisplay;
import com.example.employeemanagementapp.Repositories.AttendanceRepository;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AttendanceService {
    private static AttendanceRepository attendanceRepository;

    public AttendanceService() {
        attendanceRepository = (AttendanceRepository) new AttendanceRepository()
                .Mapper(new AttendanceMapper())
                .TableName("attendance").build();
    }

    public static String timeAgo(LocalDateTime dbDateTime) {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        Duration duration = Duration.between(dbDateTime, now);

        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return seconds + " seconds ago";
        } else if (seconds < 3600) {
            return (seconds / 60) + " minutes ago";
        } else if (seconds < 86400) {
            return (seconds / 3600) + " hours ago";
        } else {
            return (seconds / 86400) + " days ago";
        }
    }

    public AttendanceDisplay getFirstAttendance() {
        AttendanceDisplay attendanceDisplay = null;

        try {
            AttendanceDecorator attendanceDecorator = new AttendanceDecorator();
            Attendance attendance = attendanceRepository.getFirstAttendance();
            if (attendance == null) {
                attendance = new Attendance();
            } else {
                attendanceDisplay = new AttendanceDisplay(attendance.getAttendance_id(), attendance.getStatus(), timeAgo(attendance.getCheck_in().toLocalDateTime()));

                attendanceDecorator.addEmployeeName(attendanceDisplay, attendance.getEmployee_id());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return attendanceDisplay;
    }
}
