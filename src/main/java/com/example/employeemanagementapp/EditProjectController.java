package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Entities.ProjectAssignments;
import com.example.employeemanagementapp.Entities.Projects;
import com.example.employeemanagementapp.Models.ProjectDisplay;
import com.example.employeemanagementapp.Service.EmployeeService;
import com.example.employeemanagementapp.Service.ProjectService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EditProjectController {
    @FXML
    private Label project_label;

    @FXML
    private TextField project_name_input;

    @FXML
    private Label startdate_label;

    @FXML
    private DatePicker startdatepicker;

    @FXML
    private Label enddate_label;

    @FXML
    private DatePicker enddatepicker;

    @FXML
    private Label commission_label;

    @FXML
    private TextField commission_input;

    @FXML
    private Label revenue_label;

    @FXML
    private TextField revenue_input;
    
    @FXML
    private TextField search_input;

    @FXML
    private ListView<Employee> current_selected_member;

    @FXML
    private ListView<Employee> employee_for_select_list;

    @FXML
    private Button save_button;

    @FXML
    private ChoiceBox<String> is_finished;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    private ProjectService projectService;

    private EmployeeService employeeService;

    private Projects projects;

    private ObservableList<Employee> employees = null;

    private int current = 1;

    private void initChoiceBox() {
        List<String> list = new ArrayList<>();
        list.add("Completed");
        list.add("Unfinished");

        ObservableList<String> observableList = FXCollections.observableArrayList(list);

        is_finished.setItems(observableList);
    }

    private void translateText() {
        project_label.setFont(new Font("Noto Sans CJK JP", project_label.getFont().getSize()));
        project_label.setText(translator.translate(project_label.getText()));

        startdate_label.setFont(new Font("Noto Sans CJK JP", startdate_label.getFont().getSize()));
        startdate_label.setText(translator.translate(startdate_label.getText()));

        enddate_label.setFont(new Font("Noto Sans CJK JP", enddate_label.getFont().getSize()));
        enddate_label.setText(translator.translate(enddate_label.getText()));

        commission_label.setFont(new Font("Noto Sans CJK JP", commission_label.getFont().getSize()));
        commission_label.setText(translator.translate(commission_label.getText()));

        revenue_label.setFont(new Font("Noto Sans CJK JP", revenue_label.getFont().getSize()));
        revenue_label.setText(translator.translate(revenue_label.getText()));

        save_button.setText(translator.translate(save_button.getText()));
    }

    private void forceTextFieldToNumber() {
        commission_input.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*\\.?[0-9]+")) {
                return change; // allow digits
            }
            return null; // reject non-digits
        }));

        revenue_input.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*\\.?[0-9]+")) {
                return change; // allow digits
            }
            return null; // reject non-digits
        }));
    }

    private boolean validateInput() {
        if (project_name_input.getText().isEmpty()) {
            return false;
        }

        if (commission_input.getText().isEmpty()) {
            return false;
        }

        if (revenue_input.getText().isEmpty()) {
            return false;
        }

        if (startdatepicker.getValue() == null) {
            return false;
        }

        if (enddatepicker.getValue() == null) {
            return false;
        }
//        List<Employee> diff2 = employees.stream()
//                .filter(e -> !current_selected_member.getItems().contains(e))
//                .toList();
//
//        if (diff2.isEmpty()) {
//            return false;
//        }

        return true;
    }

    public void initProject(ProjectDisplay projectDisplay) {
        projects = new Projects.Builder()
                .Project_id(projectDisplay.getProjectId())
                .Project_name(projectDisplay.getProjectName())
                .Start_date(projectDisplay.getStartDate())
                .End_date(projectDisplay.getEndDate())
                .IsFinished(projectDisplay.getStatus().equals("active") ? 0 : 1)
                .Total_revenue(projectDisplay.getRevenue())
                .Commission_rate(projectDisplay.commissionRateProperty())
                .build();

    }

    public void initInput() {
        try {
            employees = FXCollections.observableArrayList(projectService.getEmployee(projects.getProject_id()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String commissionText = String.valueOf(projects.getCommission_rate());
        String revenueText = String.valueOf(projects.getTotal_revenue());

        current_selected_member.setItems(employees);
        commission_input.setText(commissionText);
        revenue_input.setText(revenueText);
        project_name_input.setText(projects.getProject_name());
        startdatepicker.setValue(projects.getStart_date().toLocalDate());
        enddatepicker.setValue(projects.getEnd_date().toLocalDate());
        is_finished.setValue(projects.getIs_finished() == 1 ? "Completed" : "Unfinished");
    }

    private Projects mapProject() {
        projects.setProject_name(project_name_input.getText());
        projects.setStart_date(Date.valueOf(startdatepicker.getValue()));
        projects.setEnd_date(Date.valueOf(enddatepicker.getValue()));
        projects.setCommission_rate(Double.parseDouble(commission_input.getText()));
        projects.setTotal_revenue(Double.parseDouble(revenue_input.getText()));

        return projects;
    }

    @FXML
    public void initialize() {
        if (!ApplicationLanguageSetter.getCurrentLanguage().equals("EN")) {
            translateText();
        }

        initChoiceBox();
        try {
            employeeService = new EmployeeService();
            projectService = new ProjectService();
            
            ObservableList<Employee> items = FXCollections.observableArrayList(employeeService.fetchList(10, current));

            employee_for_select_list.setItems(items);
            employee_for_select_list.getItems().add(new Employee.Builder().Email("Show more").First_name("...").Last_name("").build());
            
            search_input.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    ObservableList<Employee> items = FXCollections.observableArrayList(employeeService.searchEmployee(newValue));
                    employee_for_select_list.setItems(items);
                }
            });

            current_selected_member.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
                @Override
                public void changed(ObservableValue<? extends Employee> observableValue, Employee oldValue, Employee newValue) {
                    if (newValue != null) {
                        current_selected_member.getSelectionModel().clearSelection();
                        current_selected_member.getItems().remove(newValue);
                    }
                }
            });

            employee_for_select_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
                @Override
                public void changed(ObservableValue<? extends Employee> observableValue, Employee oldValue, Employee newValue) {
                        current_selected_member.getItems().add(newValue);
                }
            });

            is_finished.getSelectionModel()
                    .selectedItemProperty()
                    .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                        if (newValue.equals("Completed")) {
                            projects.setIs_finished(1);
                        } else {
                            projects.setIs_finished(0);
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        forceTextFieldToNumber();
    }

    @FXML
    protected void saveButton() {
        try {
            if (project_name_input.isDisabled()) {
                projectService.update(projects.getProject_id(), current_selected_member.getItems());
            } else {
                if (projectService.update(mapProject()) > 0) {
                    Stage stage = (Stage) save_button.getScene().getWindow();
                    stage.close();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void editButton() {
        project_name_input.setDisable(!project_name_input.isDisabled());
        startdatepicker.setDisable(!startdatepicker.isDisabled());
        enddatepicker.setDisable(!enddatepicker.isDisabled());
        commission_input.setDisable(!commission_input.isDisabled());
        revenue_input.setDisable(!revenue_input.isDisabled());
        is_finished.setDisable(!is_finished.isDisabled());
    }
}
