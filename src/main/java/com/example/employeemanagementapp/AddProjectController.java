package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Entities.Projects;
import com.example.employeemanagementapp.Service.EmployeeService;
import com.example.employeemanagementapp.Service.ProjectService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Date;

public class AddProjectController {
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
    private Button save_button;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    private ProjectService projectService;

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

    @FXML
    public void initialize() {
        if (!ApplicationLanguageSetter.getCurrentLanguage().equals("EN")) {
            translateText();
        }
        try {
            projectService = new ProjectService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        forceTextFieldToNumber();
    }

    private void forceTextFieldToNumber() {
        commission_input.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change; // allow digits
            }
            return null; // reject non-digits
        }));

        revenue_input.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
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

        return true;
    }

    private Projects mapProject() {
        Projects projects = new Projects.Builder()
                .Project_name(project_name_input.getText())
                .Start_date(Date.valueOf(startdatepicker.getValue()))
                .End_date(Date.valueOf(enddatepicker.getValue()))
                .Commission_rate(Double.parseDouble(commission_input.getText()))
                .Total_revenue(Double.parseDouble(revenue_input.getText()))
                .IsFinished(false).build();

        return projects;
    }

    @FXML
    protected void saveButton() {
        try {
            if (validateInput()) {
                Projects projects = mapProject();
                if (projectService.insert(projects) > 0) {
                    Stage stage = (Stage) save_button.getScene().getWindow();

                    stage.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
