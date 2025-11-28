package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Service.DepartmentService;
import com.example.employeemanagementapp.Service.EmployeeService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddDepartmentController {
    @FXML
    private TextField department_name_input;

    @FXML
    private ListView<Employee> department_leader_input;

    @FXML
    private TextField list_view_search;

    @FXML
    private Label selected_leader_label;

    @FXML
    private Button save_button;

    private EmployeeService employeeService;

    private DepartmentService departmentService;

    private Employee currentSelectedEmployee;

    private int current = 1;

    public AddDepartmentController() {
        try {
            employeeService = new EmployeeService();
            departmentService = new DepartmentService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        selected_leader_label.setVisible(false);

        ObservableList<Employee> items = null;
        try {
            items = FXCollections.observableArrayList(employeeService.fetchList(10, current));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        department_leader_input.setItems(items);

        list_view_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                ObservableList<Employee> items = FXCollections.observableArrayList(employeeService.searchEmployee(newValue));
                department_leader_input.setItems(items);
            }
        });

        department_leader_input.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observableValue, Employee oldValue, Employee newValue) {
                selected_leader_label.setVisible(true);
                selected_leader_label.setText(newValue.toString());
                currentSelectedEmployee = newValue;
            }
        });
    }

    private Departments mapDepartments() {
        return new Departments.Builder()
                .Department_name(department_name_input.getText())
                .Manager_id(currentSelectedEmployee.getEmployee_id())
                .build();
    }

    @FXML
    protected void saveButton() {
        try {
            departmentService.insert(mapDepartments());
            Stage stage = (Stage) save_button.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
