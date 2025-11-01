package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Builders.ProjectBuilder;
import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Entities.Projects;
import com.example.employeemanagementapp.Factories.EntityFactory;
import com.example.employeemanagementapp.Factories.EntityFactoryImpl;
import com.example.employeemanagementapp.Mapper.ProjectMapper;
import com.example.employeemanagementapp.Repositories.ProjectRepository;
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
import java.sql.SQLException;

public class ProjectController {
    @FXML
    private TextField projectid;

    @FXML
    private TextField projectname;

    @FXML
    private TextField description;

    @FXML
    private DatePicker startdate;

    @FXML
    private DatePicker enddate;

    private final Reposistory<Projects> reposistory;

    public ProjectController() throws SQLException {
        reposistory = new ProjectRepository()
                .Mapper(new ProjectMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("Projects").build();
    }

    private Projects mapEntity() {
        EntityFactory entityFactoryProvider = new EntityFactoryImpl();
        Projects entity = entityFactoryProvider.getBuilder(ProjectBuilder.class, new ProjectBuilder())
                .Project_id(Integer.parseInt(projectid.getText()))
                .Project_name(projectname.getText())
                .Description(description.getText())
                .Start_date(Date.valueOf(startdate.getValue()))
                .End_date(Date.valueOf(enddate.getValue()))
                .build();

        return entity;
    }

    private void changeController(ActionEvent event, String view) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view));
        Parent root = fxmlLoader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Insert Attendance");
        newStage.setScene(new Scene(root));
        newStage.setResizable(false);
        newStage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    protected void onEmployeeButtonClick(ActionEvent event) {
        try {
            changeController(event, "hello-view.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onAttendanceButtonClick(ActionEvent event) {
        try {
            changeController(event, "attendance.fxml");
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
            Projects entity = new Projects();
            entity.setProject_id(Integer.parseInt(projectid.getText()));

            reposistory.delete(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
