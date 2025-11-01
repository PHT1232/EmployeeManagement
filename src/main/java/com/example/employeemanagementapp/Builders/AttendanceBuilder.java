package com.example.employeemanagementapp.Builders;

import com.example.employeemanagementapp.Entities.Attendance;

import java.sql.Date;

public class AttendanceBuilder extends Builder<Attendance> {
    private int attendance_id;
    private int employee_id;
    private Date attendance_date;
    private Date check_in;
    private Date check_out;
    private String status;
    private String notes;
    private Date created_at;
    private Date updated_at;

    public AttendanceBuilder Attendance_id(int attendance_id) {
        this.attendance_id = attendance_id;
        return this;
    }

    public AttendanceBuilder Employee_id(int employee_id) {
        this.employee_id = employee_id;
        return this;
    }

    public AttendanceBuilder Attendance_date(Date attendance_date) {
        this.attendance_date = attendance_date;
        return this;
    }

    public AttendanceBuilder Check_in(Date check_in) {
        this.check_in = check_in;
        return this;
    }

    public AttendanceBuilder Check_out(Date check_out) {
        this.check_out = check_out;
        return this;
    }

    public AttendanceBuilder Status(String status) {
        this.status = status;
        return this;
    }

    public AttendanceBuilder Notes(String notes) {
        this.notes = notes;
        return this;
    }

    public AttendanceBuilder Created_at(Date created_at) {
        this.created_at = created_at;
        return this;
    }

    public AttendanceBuilder Updated_at(Date updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    @Override
    public Attendance build() {
        Attendance attendance = new Attendance();
        attendance.setAttendance_id(attendance_id);
        attendance.setEmployee_id(employee_id);
        attendance.setAttendance_date(attendance_date);
        attendance.setCheck_in(check_in);
        attendance.setCheck_out(check_out);
        attendance.setStatus(status);
        attendance.setNotes(notes);
        attendance.setCreated_at(created_at);
        attendance.setUpdated_at(updated_at);

        return attendance;
    }
}
