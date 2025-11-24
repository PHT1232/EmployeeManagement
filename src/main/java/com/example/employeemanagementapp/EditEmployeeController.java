package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Models.EmployeeDisplay;
import com.example.employeemanagementapp.Service.EmployeeService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Date;

public class EditEmployeeController {

    @FXML
    private Label add_title_label;
    @FXML
    private Label subtitle_label;

    @FXML
    private Label firstname_label;
    @FXML
    private TextField firstname_input;
    @FXML
    private Label first_name_error;

    @FXML
    private Label lastname_label;
    @FXML
    private TextField lastname_input;
    @FXML
    private Label last_name_error;

    @FXML
    private Label email_label;
    @FXML
    private TextField email_input;
    @FXML
    private Label email_error;

    @FXML
    private Label phone_label;
    @FXML
    private TextField phone_input;
    @FXML
    private Label phone_error;

    @FXML
    private Label position_label;
    @FXML
    private TextField position_input;
    @FXML
    private Label position_error;

    @FXML
    private Label salary_label;
    @FXML
    private TextField salary_input;
    @FXML
    private Label salary_error;

    @FXML
    private Label startdate_label;
    @FXML
    private DatePicker startdatepicker;
    @FXML
    private Label start_date_error;

    @FXML
    private Button save_button;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    private EmployeeService employeeService;

    private Employee employee;

    private void translateText() {
        add_title_label.setFont(new Font("Noto Sans CJK JP", add_title_label.getFont().getSize()));
        add_title_label.setText(translator.translate(add_title_label.getText()));

        subtitle_label.setFont(new Font("Noto Sans CJK JP", subtitle_label.getFont().getSize()));
        subtitle_label.setText(translator.translate(subtitle_label.getText()));

        firstname_label.setFont(new Font("Noto Sans CJK JP", firstname_label.getFont().getSize()));
        firstname_label.setText(translator.translate(firstname_label.getText()));

        lastname_label.setFont(new Font("Noto Sans CJK JP", lastname_label.getFont().getSize()));
        lastname_label.setText(translator.translate(lastname_label.getText()));

        email_label.setFont(new Font("Noto Sans CJK JP", email_label.getFont().getSize()));
        email_label.setText(translator.translate(email_label.getText()));

        phone_label.setFont(new Font("Noto Sans CJK JP", phone_label.getFont().getSize()));
        phone_label.setText(translator.translate(phone_label.getText()));

        salary_label.setFont(new Font("Noto Sans CJK JP", salary_label.getFont().getSize()));
        salary_label.setText(translator.translate(salary_label.getText()));

        startdate_label.setFont(new Font("Noto Sans CJK JP", startdate_label.getFont().getSize()));
        startdate_label.setText(translator.translate(startdate_label.getText()));

        position_label.setFont(new Font("Noto Sans CJK JP", position_label.getFont().getSize()));
        position_label.setText(translator.translate(position_label.getText()));

        save_button.setText(translator.translate(save_button.getText()));
    }

    public EditEmployeeController() throws Exception {
        employeeService = new EmployeeService();
    }

    public void initEmployee(EmployeeDisplay employeeDisplay) {
        String[] namePart = employeeDisplay.getEmployeeName().split(" ");
        String[] emailPart = employeeDisplay.getContact().split("\n");
        Date date = Date.valueOf(employeeDisplay.getStartDate().toLocalDate());
        employee = new Employee.Builder()
                .Employee_id(employeeDisplay.getEmployeeId())
                .First_name(namePart[0])
                .Last_name(namePart[1])
                .Email(emailPart[0])
                .Phone(emailPart[1])
                .Hire_date(date)
                .Salary(employeeDisplay.getSalary())
                .Position(employeeDisplay.getPosition())
                .build();
    }

    private void displayErrorMessage() {
        first_name_error.setVisible(firstname_input.getText().isEmpty());
        last_name_error.setVisible(lastname_input.getText().isEmpty());
        email_error.setVisible(email_input.getText().isEmpty());
        phone_error.setVisible(phone_input.getText().isEmpty());
        position_error.setVisible(position_input.getText().isEmpty());
        salary_error.setVisible(salary_input.getText().isEmpty());
        start_date_error.setVisible(startdatepicker.getValue() == null);
    }

    public void initInput() {
        firstname_input.setText(employee.getFirst_name());
        lastname_input.setText(employee.getLast_name());
        email_input.setText(employee.getEmail());
        phone_input.setText(employee.getPhone());
        position_input.setText(employee.getPosition());
        salary_input.setText(String.valueOf(employee.getSalary()));
        startdatepicker.setValue(employee.getHire_date().toLocalDate());
    }

    private boolean validateInput() {
        if (firstname_input.getText().isEmpty()) {
            return false;
        }

        if (lastname_input.getText().isEmpty()) {
            return false;
        }

        if (email_input.getText().isEmpty()) {
            return false;
        }

        if (phone_input.getText().isEmpty()) {
            return false;
        }

        if (position_input.getText().isEmpty()) {
            return false;
        }

        if (salary_input.getText().isEmpty()) {
            return false;
        }

        if (startdatepicker.getValue() == null) {
            return false;
        }

        return true;
    }

    @FXML
    public void initialize() {
        if (!ApplicationLanguageSetter.getCurrentLanguage().equals("EN")) {
            translateText();
        }

    }

    private void buildEmployee() {
        employee.setFirst_name(firstname_input.getText());
        employee.setLast_name(lastname_input.getText());
        employee.setEmail(email_input.getText());
        employee.setPhone(phone_input.getText());
        employee.setPosition(position_input.getText());
        employee.setSalary(Double.parseDouble(salary_input.getText()));
        employee.setHire_date(Date.valueOf(startdatepicker.getValue()));
    }

    @FXML
    protected void enableEdit() {
        firstname_input.setDisable(!firstname_input.isDisabled());
        lastname_input.setDisable(!lastname_input.isDisabled());
        email_input.setDisable(!email_input.isDisabled());
        phone_input.setDisable(!phone_input.isDisabled());
        position_input.setDisable(!position_input.isDisabled());
        salary_input.setDisable(!salary_input.isDisabled());

        startdatepicker.setDisable(!startdatepicker.isDisabled());
        save_button.setDisable(!save_button.isDisabled());
    }

    @FXML
    protected void saveEmployeeToDatabase() {
        displayErrorMessage();
        try {
            if (validateInput()) {
                buildEmployee();
                if (employeeService.updateEmployee(employee)>0) {
                    Stage stage = (Stage) save_button.getScene().getWindow();

                    stage.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
