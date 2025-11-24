package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Entities.Attendance;
import com.example.employeemanagementapp.Mapper.AttendanceMapper;
import com.example.employeemanagementapp.Models.AttendanceDisplay;
import com.example.employeemanagementapp.Models.RecentActivityDisplay;
import com.example.employeemanagementapp.Repositories.AttendanceRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AttendanceService {
    private static AttendanceRepository attendanceRepository;
    private PaginationService<Attendance> paginationService;

    public AttendanceService() {
        attendanceRepository = (AttendanceRepository) new AttendanceRepository()
                .Mapper(new AttendanceMapper())
                .TableName("attendance").build();

        paginationService = new PaginationServiceImpl<>(attendanceRepository);
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

    public AttendanceDisplay getNewCheckIn() throws Exception {
        AttendanceDecorator decorator = new AttendanceDecorator();
        List<Attendance> list = attendanceRepository.getAttendanceWithLimit(1);
        if (!list.isEmpty()) {
            Attendance attendance = list.getFirst();
            AttendanceDisplay attendanceDisplay = new AttendanceDisplay.Builder()
                    .AttendanceId(attendance.getAttendance_id())
                    .Status(attendance.getCheck_out() == null ? "Checked In" : "Checked Out")
                    .CheckIn(String.valueOf(attendance.getCheck_in()))
                    .CheckOut(String.valueOf(attendance.getCheck_out()))
                    .build();

            decorator.addDepartmentName(attendanceDisplay, attendance.getEmployee_id());
            decorator.addEmployeeName(attendanceDisplay, attendance.getEmployee_id());

            return attendanceDisplay;
        }

        return new AttendanceDisplay.Builder().build();
    }

    public RecentActivityDisplay getFirstAttendance() {
        RecentActivityDisplay attendanceDisplay = new RecentActivityDisplay();

        try {
            AttendanceDecorator attendanceDecorator = new AttendanceDecorator();
            List<Attendance> list = attendanceRepository.getAttendanceWithLimit(1);
            if (!list.isEmpty()) {
                Attendance attendance = list.getFirst();
                attendanceDisplay = new RecentActivityDisplay(attendance.getAttendance_id(), attendance.getStatus(), timeAgo(attendance.getCheck_in().toLocalDateTime()));

                attendanceDecorator.addEmployeeName(attendanceDisplay, attendance.getEmployee_id());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return attendanceDisplay;
    }

    public List<AttendanceDisplay> getAttendanceList() throws Exception {
        List<Attendance> list = attendanceRepository.getAttendanceWithLimit(10);
        List<AttendanceDisplay> returnList = new ArrayList<>();

        AttendanceDecorator decorator = new AttendanceDecorator();
        for (Attendance attendance : list) {
            AttendanceDisplay attendanceDisplay = new AttendanceDisplay.Builder()
                    .AttendanceId(attendance.getAttendance_id())
                    .Status(attendance.getStatus())
                    .CheckIn(String.valueOf(attendance.getCheck_in()))
                    .CheckOut(attendance.getCheck_out() == null ? "--" : String.valueOf(attendance.getCheck_out()))
                    .build();

            decorator.addDepartmentName(attendanceDisplay, attendance.getEmployee_id());
            decorator.addEmployeeName(attendanceDisplay, attendance.getEmployee_id());

            returnList.add(attendanceDisplay);
        }

        return returnList;
    }

    public int getTotalCheckIn() {
        int totalCheckIn = 0;
        LocalDate localDate = LocalDate.now();
        String sql = "SELECT COUNT(1) as num FROM attendance WHERE attendance_date = '" + localDate + "'" ;

        try {
             totalCheckIn = attendanceRepository.getNumberBySql(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return totalCheckIn;
    }

    public int getTotalCheckOut() {
        int totalCheckOut = 0;
        LocalDate localDate = LocalDate.now();
        String sql = "SELECT COUNT(1) as num FROM attendance WHERE attendance_date = '" + localDate + "' AND check_out != 'NULL'" ;

        try {
            totalCheckOut = attendanceRepository.getNumberBySql(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return totalCheckOut;
    }

    public int getTotalOvertime() {
        int total = 0;
        String sql = "SELECT SUM(overtime) as num FROM attendance";

        try {
            total = attendanceRepository.getNumberBySql(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return total;
    }
}
