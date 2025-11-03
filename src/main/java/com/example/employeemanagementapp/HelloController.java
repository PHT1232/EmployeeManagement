package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Builders.EmployeeBuilder;
import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;
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

public class HelloController {
    @FXML
    private TextField employeeid;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField position;
    @FXML
    private TextField salary;
    @FXML
    private DatePicker hire_date;
    @FXML
    private TextField department_id;
    @FXML
    private TextField manager_id;

    private final EmployeeRepository reposistory;

    public HelloController() throws Exception {
        reposistory = (EmployeeRepository) new EmployeeRepository()
                .Mapper(new EmployeeMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("Employees").build();
    }

    private Employee mapEmployee() {
        Employee employee = new EmployeeBuilder()
                .Employee_id(Integer.parseInt(employeeid.getText()))
                .First_name(first_name.getText())
                .Last_name(last_name.getText())
                .Email(email.getText())
                .Phone(phone.getText())
                .Position(position.getText())
                .Salary(Double.parseDouble(salary.getText()))
                .Hire_date(Date.valueOf(hire_date.getValue()))
                .Department_id(Integer.parseInt(department_id.getText()))
                .Manager_id(Integer.parseInt(manager_id.getText()))
                .build();

        return employee;
    }

    @FXML
    protected void onAttendanceButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("attendance.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            newStage.setTitle("Insert Attendance");
            newStage.setScene(scene);
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

            reposistory.update(mapEmployee());
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
            reposistory.insert(mapEmployee());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        try {
            Employee employee = new Employee();
            employee.setEmployee_id(Integer.parseInt(employeeid.getText()));

            reposistory.delete(employee);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
