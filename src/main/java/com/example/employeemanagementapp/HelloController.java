package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;
import com.example.employeemanagementapp.Repositories.Reposistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Button dashbroad_nav_button;
    @FXML
    private Button all_button_filter;
    @FXML
    private Button checked_in_button_filter;
    @FXML
    private Button checked_out_button_filter;
    @FXML
    private BorderPane main_borderpane;
    @FXML
    private ScrollPane main_scrollpane;
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
    private ChoiceBox<String> department_filter_box;

    private final EmployeeRepository reposistory;

    public HelloController() throws Exception {
        reposistory = (EmployeeRepository) new EmployeeRepository()
                .Mapper(new EmployeeMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("Employees").build();
    }

    private void initChoiceBox() {
        List<String> itemList = new ArrayList<>();
        itemList.add("All Departments");
        itemList.add("Engineering");
        itemList.add("Marketing");
        itemList.add("Sales");
        itemList.add("HR");

        ObservableList<String> observableItemList = FXCollections.observableArrayList(itemList);

        department_filter_box.setItems(observableItemList);
        department_filter_box.setValue("All Departments");
    }

    private void initButtonStyleClass() {
        dashbroad_nav_button.getStyleClass().add("nav_button_select");
        all_button_filter.getStyleClass().setAll("button-selected");
    }

    @FXML
    public void initialize() {
        initButtonStyleClass();
        initChoiceBox();

        main_scrollpane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                attachStageListener(newScene);
            }
        });
    }

    private void attachStageListener(Scene scene) {
        scene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
            if (newWindow instanceof Stage stage) {
                stage.setOnShown(event -> onStageShown(stage));
            }
        });
    }

    private void onStageShown(Stage stage) {
        stage.widthProperty().addListener((o, oldVal, newVal) -> {
            changeBorderpaneSize(newVal.doubleValue());
        });
    }

    private void changeBorderpaneSize(double newVal) {
        main_borderpane.setPrefWidth(newVal - 234);
    }

    private Employee mapEmployee() {
        Employee employee = new Employee.Builder()
                .Employee_id(Integer.parseInt(employeeid.getText()))
                .First_name(first_name.getText())
                .Last_name(last_name.getText())
                .Email(email.getText())
                .Phone(phone.getText())
                .Position(position.getText())
                .Salary(Double.parseDouble(salary.getText()))
                .Hire_date(Date.valueOf(hire_date.getValue()))
                .Department_id(Integer.parseInt(department_id.getText()))
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
    protected void onAllFilterClick() {
        all_button_filter.getStyleClass().setAll("button-selected");
        checked_in_button_filter.getStyleClass().setAll("button-regular");
        checked_out_button_filter.getStyleClass().setAll("button-regular");
    }

    @FXML
    protected void onCheckedInFilterClick() {
        all_button_filter.getStyleClass().setAll("button-regular");
        checked_in_button_filter.getStyleClass().setAll("button-selected");
        checked_out_button_filter.getStyleClass().setAll("button-regular");
    }

    @FXML
    protected void onCheckedOutFilterClick() {
        all_button_filter.getStyleClass().setAll("button-regular");
        checked_in_button_filter.getStyleClass().setAll("button-regular");
        checked_out_button_filter.getStyleClass().setAll("button-selected");
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
            Employee employee = new Employee.Builder().build();
            employee.setEmployee_id(Integer.parseInt(employeeid.getText()));

            reposistory.delete(employee);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
