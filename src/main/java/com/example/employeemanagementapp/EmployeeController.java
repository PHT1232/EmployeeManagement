package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Models.DepartmentDisplay;
import com.example.employeemanagementapp.Models.EmployeeDisplay;
import com.example.employeemanagementapp.Service.DepartmentService;
import com.example.employeemanagementapp.Service.EmployeeService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class EmployeeController {
    @FXML
    private ScrollPane main_scrollpane;
    @FXML
    private BorderPane main_borderpane;

    @FXML
    private ChoiceBox<String> department_filter_box;
    @FXML
    private DatePicker hire_date;
    @FXML
    private Label main_header_label;
    @FXML
    private Label main_date_label;
    @FXML
    private Label first_card_label;
    @FXML
    private Label active_label;
    @FXML
    private Label current_present_label;
    @FXML
    private Label left_for_today_label;
    @FXML
    private Label today_attendance_label;
    @FXML
    private Label second_card_label;
    @FXML
    private Label third_card_label;
    @FXML
    private Label fouth_card_label;
    @FXML
    private Label employee_status_label;
    @FXML
    private Label select_department_label;
    @FXML
    private Label below_top10_label;
    @FXML
    private Label first_num_label;
    @FXML
    private Label second_num_label;
    @FXML
    private Label third_num_label;
    @FXML
    private Label forth_num_label;

    @FXML
    private TableView employee_table;
    @FXML
    private TableView department_table;

    private DepartmentService departmentService;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    private EmployeeService employeeService;

    int currentEmployeePage = 1;

    public EmployeeController() throws Exception {

        departmentService = new DepartmentService();
        employeeService = new EmployeeService();
    }

    private void translateText() {
        main_header_label.setFont(new Font("Noto Sans CJK JP", main_header_label.getFont().getSize()));
        main_header_label.setText(translator.translate(main_header_label.getText()));

        main_date_label.setFont(new Font("Noto Sans CJK JP", main_date_label.getFont().getSize()));
        main_date_label.setText(translator.translate(main_date_label.getText()));

        first_card_label.setFont(new Font("Noto Sans CJK JP", first_card_label.getFont().getSize()));
        first_card_label.setText(translator.translate(first_card_label.getText()));

        active_label.setFont(new Font("Noto Sans CJK JP", active_label.getFont().getSize()));
        active_label.setText(translator.translate(active_label.getText()));

        current_present_label.setFont(new Font("Noto Sans CJK JP", current_present_label.getFont().getSize()));
        current_present_label.setText(translator.translate(current_present_label.getText()));

        left_for_today_label.setFont(new Font("Noto Sans CJK JP", left_for_today_label.getFont().getSize()));
        left_for_today_label.setText(translator.translate(left_for_today_label.getText()));

        today_attendance_label.setFont(new Font("Noto Sans CJK JP", today_attendance_label.getFont().getSize()));
        today_attendance_label.setText(translator.translate(today_attendance_label.getText()));

        second_card_label.setFont(new Font("Noto Sans CJK JP", second_card_label.getFont().getSize()));
        second_card_label.setText(translator.translate(second_card_label.getText()));

        third_card_label.setFont(new Font("Noto Sans CJK JP", third_card_label.getFont().getSize()));
        third_card_label.setText(translator.translate(third_card_label.getText()));

        fouth_card_label.setFont(new Font("Noto Sans CJK JP", fouth_card_label.getFont().getSize()));
        fouth_card_label.setText(translator.translate(fouth_card_label.getText()));

        employee_status_label.setFont(new Font("Noto Sans CJK JP", employee_status_label.getFont().getSize()));
        employee_status_label.setText(translator.translate(employee_status_label.getText()));

        below_top10_label.setFont(new Font("Noto Sans CJK JP", below_top10_label.getFont().getSize()));
        below_top10_label.setText(translator.translate(below_top10_label.getText()));
    }

    private void populateEmployeeTable() throws Exception {
        ObservableList<EmployeeDisplay> employees = FXCollections.observableArrayList(employeeService.fetchDisplayList(10, currentEmployeePage));
        employee_table.setItems(employees);
    }

    private void populateDepartmentTable() throws Exception {
        ObservableList<DepartmentDisplay> departments = FXCollections.observableArrayList(departmentService.fetchListWithEmployeeName(10, currentDepartmentPage));
        department_table.setItems(departments);
    }

    private void populateDepartmentColumn() {
        TableColumn<DepartmentDisplay, Integer> idCol = new TableColumn<>("Id");

        TableColumn<DepartmentDisplay, String> nameCol = new TableColumn<>("Name");

        TableColumn<DepartmentDisplay, String> managerCol = new TableColumn<>("Manager");

        TableColumn<DepartmentDisplay, Date> createdAtCol = new TableColumn<>("Created At");

        TableColumn<DepartmentDisplay, Date> updatedAtCol = new TableColumn<>("Updated At");

        idCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getDepartment_id()).asObject());

        nameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDepartment_name()));

        managerCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getManager()));

        createdAtCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreated_at()));

        updatedAtCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getUpdated_at()));


        department_table.getColumns().addAll(idCol, nameCol, managerCol, createdAtCol, updatedAtCol);
        department_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    }

    private void populateEmployeeColumn() {
        TableColumn<EmployeeDisplay, String> idCol = new TableColumn<>("Employee");

        TableColumn<EmployeeDisplay, String> nameCol = new TableColumn<>("Contact");

        TableColumn<EmployeeDisplay, String> roleCol = new TableColumn<>("Role");

        TableColumn<EmployeeDisplay, String> positionCol = new TableColumn<>("Position");

        TableColumn<EmployeeDisplay, Double> salaryCol = new TableColumn<>("Salary");

        TableColumn<EmployeeDisplay, String> tenure = new TableColumn<>("Tenure");

        TableColumn<EmployeeDisplay, Date> startDateCol = new TableColumn<>("Start Date");

        idCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmployeeName()));

        nameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getContact()));

        roleCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRole()));

        positionCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPosition()));

        salaryCol.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getSalary()).asObject());

        tenure.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getHire_date()));

        startDateCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStartDate()));


        employee_table.getColumns().addAll(idCol, nameCol, roleCol, tenure, positionCol, salaryCol, startDateCol);
        employee_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    }

    @FXML
    public void initialize() {
        if (!ApplicationLanguageSetter.getCurrentLanguage().equals("EN")) {
            translateText();
        }

        employee_table.setRowFactory(tv -> {
            TableRow<EmployeeDisplay> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    EmployeeDisplay clicked = row.getItem();
                    openEditModal(clicked);
                }
            });
            return row;
        });

        try {
            Thread departMentThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        populateDepartmentColumn();
                        populateDepartmentTable();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            departMentThread.start();

            Thread employeeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        populateEmployeeColumn();
                        populateEmployeeTable();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            employeeThread.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        main_scrollpane.setFitToWidth(true);
        first_num_label.setText(String.valueOf(employeeService.getTotalEmployee()));
        second_num_label.setText(String.valueOf(departmentService.getTotalDepartment()));
        third_num_label.setText(employeeService.avgTenure() + " years");
    }

    int currentDepartmentPage = 1;

    @FXML
    protected void prevDepartment() {
        currentDepartmentPage--;
        try {
            if (currentDepartmentPage > 0) {
                populateDepartmentTable();
            } else {
                currentDepartmentPage = 1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void nextDepartment() {
        currentDepartmentPage++;
        try {
            populateDepartmentTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void prevEmployee() {
        currentEmployeePage--;
        try {
            if (currentEmployeePage > 0) {
                populateEmployeeTable();
            } else {
                currentEmployeePage = 1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void nextEmployee() {
        currentEmployeePage++;
        try {
            populateEmployeeTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void openFxmlModal(String filename) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openEditModal(EmployeeDisplay employeeDisplay) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_employee.fxml"));
            Parent root = loader.load();

            EditEmployeeController editEmployeeController = loader.getController();

            editEmployeeController.initEmployee(employeeDisplay);
            editEmployeeController.initInput();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void openAddEmployeeModal(ActionEvent event) {
        openFxmlModal("add_employee.fxml");
    }

    @FXML
    protected void openAddDepartmentModal(ActionEvent event) {
        openFxmlModal("add_department.fxml");
    }
}
