package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Models.DepartmentDisplay;
import com.example.employeemanagementapp.Service.DepartmentService;
import com.example.employeemanagementapp.Service.EmployeeService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditDepartmentsController {
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
    private TextField employee_view_search;

    @FXML
    private TextArea description_input;

    @FXML
    private ListView<Employee> department_leader_input;

    @FXML
    private ListView<Employee> current_selected_member;

    @FXML
    private ListView<Employee> employee_for_select_list;

    private List<Employee> initialEmployeeList = new ArrayList<>();

    @FXML
    private Button save_button;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    private EmployeeService employeeService;

    private DepartmentService departmentService;

    private List<Employee> currentSelectEmployee = new ArrayList<>();

    private Employee currentSelectedEmployee;

    private DepartmentDisplay departments;

    private int current = 1;

    public EditDepartmentsController() {
        try {
            employeeService = new EmployeeService();
            departmentService = new DepartmentService();
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

    public void initDepartment(DepartmentDisplay departmentDisplay) {
        departments = departmentDisplay;
    }

    public void initInput() {
        ObservableList<Employee> employees =null;
        try {
            initialEmployeeList = departmentService.getEmployee(departments.getDepartment_id());
            employees = FXCollections.observableArrayList(initialEmployeeList);

            ObservableList<Employee> items = null;
            if (!initialEmployeeList.isEmpty()) {
                int[] array = new int[initialEmployeeList.size()];
                for (int i = 0; i < initialEmployeeList.size(); i++) {
                    array[i] = initialEmployeeList.get(i).getEmployee_id();
                }
                items = FXCollections.observableArrayList(employeeService.fetchPaginationWithDifferentId(15, current, array));
            } else {
                items = FXCollections.observableArrayList(employeeService.fetchList(15, current));
            }
            employee_for_select_list.setItems(items);
            employee_for_select_list.getItems().add(new Employee.Builder().Email("Show more").First_name("...").Last_name("").build());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        current_selected_member.setItems(employees);
        department_name_input.setText(departments.getDepartment_name());
        selected_leader_label.setText(departments.getManager());
        selected_leader_label.setVisible(true);
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

        employee_view_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                ObservableList<Employee> items = FXCollections.observableArrayList(employeeService.searchEmployee(newValue));
                employee_for_select_list.setItems(items);
            }
        });

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

        current_selected_member.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observableValue, Employee oldValue, Employee newValue) {
                if (newValue != null) {
                    javafx.application.Platform.runLater(() -> {
                        current_selected_member.getSelectionModel().clearSelection();
                        current_selected_member.getItems().remove(newValue);
                    });
                }
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

    private Departments mapDepartments() {
        return new Departments.Builder()
                .Department_id(departments.getDepartment_id())
                .Department_name(department_name_input.getText())
                .Manager_id(currentSelectedEmployee == null ? departments.getManager_id() : currentSelectedEmployee.getEmployee_id())
                .build();
    }

    @FXML
    protected void saveButton() {
        try {
            List<Employee> diff2 = initialEmployeeList.stream()
                    .filter(e -> !current_selected_member.getItems().contains(e))
                    .toList();

            List<Employee> addDiff = current_selected_member.getItems().stream()
                    .filter(e -> !initialEmployeeList.contains(e))
                    .toList();

            if (!addDiff.isEmpty()) {
                departmentService.addEmployeeToDepartment(departments.getDepartment_id(), addDiff);
            }

            if (!diff2.isEmpty()) {
                diff2.forEach(employee -> departmentService.deleteEmployeeFromDepartment(departments.getDepartment_id(), employee.getEmployee_id()));
            }

            if (departmentService.update(mapDepartments()) > 0) {
                Stage stage = (Stage) save_button.getScene().getWindow();
                stage.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
