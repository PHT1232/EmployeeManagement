package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Entities.Attendance;
import com.example.employeemanagementapp.Mapper.AttendanceMapper;
import com.example.employeemanagementapp.Repositories.AttendanceRepository;
import com.example.employeemanagementapp.Repositories.Reposistory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class AttendanceController {
    @FXML
    private TextField attendanceid;

    @FXML
    private TextField employeeid;

    @FXML
    private DatePicker attendancedate;

    @FXML
    private DatePicker checkin;

    @FXML
    private DatePicker checkout;

    @FXML
    private TextField status;

    @FXML
    private TextField notes;

    private final Reposistory<Attendance> reposistory;

    public AttendanceController() throws Exception {
        this.reposistory = new AttendanceRepository()
                .Mapper(new AttendanceMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("Attendance").build();
    }

    private Attendance mapEntity() {
        Attendance entity = new Attendance.Builder()
                .Attendance_id(Integer.parseInt(attendanceid.getText()))
                .Employee_id(Integer.parseInt(employeeid.getText()))
                .Attendance_date(Date.valueOf(attendancedate.getValue()))
                .Check_in(Date.valueOf(checkin.getValue()))
                .Check_out(Date.valueOf(checkout.getValue()))
                .Status(status.getText())
                .Notes(notes.getText())
                .build();

        return entity;
    }

    @FXML
    protected void onEmployeeButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            newStage.setTitle("Insert Attendance");
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            newStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onUpdateButtonClick() {
        try {
            this.reposistory.update(mapEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onListAllButtonClick() {
        try {
            reposistory.findAll().forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        try {
            reposistory.insert(mapEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        try {
            Attendance entity = new Attendance();
            entity.setAttendance_id(Integer.parseInt(attendanceid.getText()));

            reposistory.delete(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
