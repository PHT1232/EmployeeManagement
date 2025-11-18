package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Entities.Employee;
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
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddDepartmentController {
    @FXML
    private Label department_label;

    @FXML
    private Label description_label;

    @FXML
    private Label department_leader_label;

    @FXML
    private Label department_member_label;

    @FXML
    private Label selected_leader_label;

    @FXML
    private TextField department_name_input;

    @FXML
    private TextField list_view_search;

    @FXML
    private TextArea description_input;

    @FXML
    private ListView<Employee> department_leader_input;

    @FXML
    private ListView<Employee> current_selected_member;

    @FXML
    private ListView<Employee> employee_for_select_list;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    private EmployeeService employeeService;

    private List<Employee> currentSelectEmployee = new ArrayList<>();

    private int current = 1;

    public AddDepartmentController() {
        try {
            employeeService = new EmployeeService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void translateText() {
        department_label.setFont(new Font("Noto Sans CJK JP", department_label.getFont().getSize()));
        department_label.setText(translator.translate(department_label.getText()));

        description_label.setFont(new Font("Noto Sans CJK JP", description_label.getFont().getSize()));
        description_label.setText(translator.translate(description_label.getText()));

        department_leader_label.setFont(new Font("Noto Sans CJK JP", description_label.getFont().getSize()));
        department_leader_label.setText(translator.translate(department_leader_label.getText()));

        department_member_label.setFont(new Font("Noto Sans CJK JP", department_member_label.getFont().getSize()));
        department_member_label.setText(translator.translate(department_member_label.getText()));
    }

    @FXML
    public void initialize() {
        if (!ApplicationLanguageSetter.getCurrentLanguage().equals("EN")) {
            translateText();
        }

        selected_leader_label.setVisible(false);

        ObservableList<Employee> items = null;
        try {
            items = FXCollections.observableArrayList(employeeService.fetchList(10, current));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        department_leader_input.setItems(items);
        employee_for_select_list.setItems(items);
        employee_for_select_list.getItems().add(new Employee.Builder().Email("Show more").First_name("...").build());

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
            }
        });

        employee_for_select_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observableValue, Employee oldValue, Employee newValue) {
                if (newValue.getEmail().equals("Show more")) {
                    try {
                        current++;
                        employee_for_select_list.getItems().addAll(employeeService.fetchList(10, current));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    current_selected_member.getItems().add(newValue);
                }
            }
        });
    }
}
