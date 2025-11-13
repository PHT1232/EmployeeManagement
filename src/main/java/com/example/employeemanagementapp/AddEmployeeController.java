package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Service.DepartmentService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import org.controlsfx.control.PropertySheet;

public class AddEmployeeController {
    @FXML
    private Label add_title_label;

    @FXML
    private Label subtitle_label;

    @FXML
    private Label firstname_label;

    @FXML
    private TextField firtname_input;

    @FXML
    private Label lastname_label;

    @FXML
    private TextField lastname_input;

    @FXML
    private Label email_label;

    @FXML
    private TextField email_input;

    @FXML
    private Label phone_label;

    @FXML
    private TextField phone_input;

    @FXML
    private Label position_label;

    @FXML
    private TextField position_input;

    @FXML
    private Label salary_label;

    @FXML
    private TextField salary_input;

    @FXML
    private Label startdate_label;

    @FXML
    private DatePicker startdatepicker;

    @FXML
    private Button save_button;

    @FXML
    private Label first_name_error;

    @FXML
    private Label last_name_error;

    @FXML
    private Label email_error;

    @FXML
    private Label phone_error;

    @FXML
    private Label position_error;

    @FXML
    private Label salary_error;

    @FXML
    private Label start_date_error;

    @FXML
    private Label departments_label;

    @FXML
    private ComboBox<Departments> departments_combobox;

    private Departments selectedDepartment;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    private DepartmentService departmentService;

    public AddEmployeeController() throws Exception {
        departmentService = new DepartmentService();
    }

    @FXML
    public void initialize() {
        if (!ApplicationLanguageSetter.getCurrentLanguage().equals("EN")) {
            translateText();
        }

        forceTextFieldToNumber();
        initCombobox();
    }

    private void initCombobox() {
        ObservableList<Departments> items = FXCollections.observableArrayList(
                new Departments.Builder().Department_id(1).Department_name("phat").build(),
                new Departments.Builder().Department_id(2).Department_name("Engineering").build());

        departments_combobox.setItems(items);

        // Add listener to editor
        departments_combobox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                departments_combobox.setItems(items);
            } else {
                ObservableList<Departments> filtered = FXCollections.observableArrayList();
                for (Departments item : items) {
                    if (item.getDepartment_name().startsWith(newVal.toLowerCase())) {
                        filtered.add(item);
                    }
                }
                departments_combobox.setItems(filtered);
                departments_combobox.show();
            }
        });
    }

    private void forceTextFieldToNumber() {
        phone_input.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change; // allow digits
            }
            return null; // reject non-digits
        }));

        salary_input.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change; // allow digits
            }
            return null; // reject non-digits
        }));
    }

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

        departments_label.setFont(new Font("Noto Sans CJK JP", departments_label.getFont().getSize()));
        departments_label.setText(translator.translate(departments_label.getText()));

        save_button.setText(translator.translate(save_button.getText()));
    }

    private void displayErrorMessage() {
        first_name_error.setVisible(firtname_input.getText().isEmpty());
        last_name_error.setVisible(lastname_input.getText().isEmpty());
        email_error.setVisible(email_input.getText().isEmpty());
        phone_error.setVisible(phone_input.getText().isEmpty());
        position_error.setVisible(position_input.getText().isEmpty());
        salary_error.setVisible(salary_input.getText().isEmpty());
        start_date_error.setVisible(startdatepicker.getValue() == null);
    }

    private boolean validateInput() {
        if (firtname_input.getText().isEmpty()) {
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

    private Employee buildEmployee() {
        Employee employee = new Employee.Builder()
                .First_name(firtname_input.getText())
                .Last_name(lastname_input.getText())
                .Email(email_input.getText())
                .Phone(phone_input.getText())
                .Position(position_input.getText())
                .Department_id(selectedDepartment.getDepartment_id())
                .build();

        return employee;
    }

    @FXML
    protected void onDepartmentSelected(ActionEvent event) {
        selectedDepartment = departments_combobox.getSelectionModel().getSelectedItem();
    }

    @FXML
    protected void saveEmployeeToDatabase() {
        displayErrorMessage();
        try {
            System.out.println(departmentService.fetchList(10, 1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
