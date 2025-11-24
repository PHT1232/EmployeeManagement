package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Models.AttendanceDisplay;
import com.example.employeemanagementapp.Models.RecentActivityDisplay;
import com.example.employeemanagementapp.Models.Top10EmployeeDisplay;
import com.example.employeemanagementapp.Service.AttendanceService;
import com.example.employeemanagementapp.Service.EmployeeService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;

import java.util.*;

public class DashbroadController {
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
    private ChoiceBox<String> department_filter_box;
    @FXML
    private DatePicker hire_date;

    @FXML
    private Text main_header_label;
    @FXML
    private Text main_date_label;
    @FXML
    private Text first_card_label;
    @FXML
    private Text active_label;
    @FXML
    private Text current_present_label;
    @FXML
    private Text left_for_today_label;
    @FXML
    private Text today_attendance_label;
    @FXML
    private Text second_card_label;
    @FXML
    private Text third_card_label;
    @FXML
    private Text fouth_card_label;
    @FXML
    private Text employee_status_label;
    @FXML
    private Text real_time_label;
    @FXML
    private Text recent_activity_label;
    @FXML
    private Text latest_checkin_out_event_label;
    @FXML
    private Text top10_label;
    @FXML
    private Text below_top10_label;
    @FXML
    private Text first_num_label;
    @FXML
    private Text second_num_label;
    @FXML
    private Text third_num_label;
    @FXML
    private Text forth_num_label;
    @FXML
    private VBox activityContainer;
    @FXML
    private TableView<AttendanceDisplay> attendance_table;
    @FXML
    private TableView<Top10EmployeeDisplay> top10_table;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    private EmployeeService employeeService;

    public DashbroadController() {
        try {
            employeeService = new EmployeeService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

        real_time_label.setFont(new Font("Noto Sans CJK JP", real_time_label.getFont().getSize()));
        real_time_label.setText(translator.translate(real_time_label.getText()));

        recent_activity_label.setFont(new Font("Noto Sans CJK JP", recent_activity_label.getFont().getSize()));
        recent_activity_label.setText(translator.translate(recent_activity_label.getText()));

        latest_checkin_out_event_label.setFont(new Font("Noto Sans CJK JP", latest_checkin_out_event_label.getFont().getSize()));
        latest_checkin_out_event_label.setText(translator.translate(latest_checkin_out_event_label.getText()));

        top10_label.setFont(new Font("Noto Sans CJK JP", top10_label.getFont().getSize()));
        top10_label.setText(translator.translate(top10_label.getText()));

        below_top10_label.setFont(new Font("Noto Sans CJK JP", below_top10_label.getFont().getSize()));
        below_top10_label.setText(translator.translate(below_top10_label.getText()));


        all_button_filter.setText(translator.translate(all_button_filter.getText()));
        checked_in_button_filter.setText(translator.translate(checked_in_button_filter.getText()));
        checked_out_button_filter.setText(translator.translate(checked_out_button_filter.getText()));
    }

    public void addActivity(String name, String status, String timeAgo) {
        VBox entryBox = new VBox();
        entryBox.setStyle("-fx-border-color: transparent transparent black transparent;");
        VBox.setMargin(entryBox, new Insets(10, 0, 0, 0));

        Text nameText = new Text(name);
        nameText.setStyle("-fx-fill: green;");
        nameText.setFont(Font.font(20));

        Text statusText = new Text(status);
        statusText.setStyle("-fx-fill: gray;");
        statusText.setFont(Font.font(15));
        VBox.setMargin(statusText, new Insets(10, 0, 0, 0));

        Text timeText = new Text(timeAgo);
        timeText.setFont(Font.font(15));
        VBox.setMargin(timeText, new Insets(5, 0, 0, 0));

        entryBox.getChildren().addAll(nameText, statusText, timeText);

        activityContainer.getChildren().add(entryBox);
    }

    private void populateAttendanceTableColumn() {
        TableColumn<AttendanceDisplay, String> nameCol = new TableColumn<>("Employee Name");
        TableColumn<AttendanceDisplay, String> deptCol = new TableColumn<>("Department");
        TableColumn<AttendanceDisplay, String> statusCol = new TableColumn<>("Status");
        TableColumn<AttendanceDisplay, String> checkInCol = new TableColumn<>("Check In");
        TableColumn<AttendanceDisplay, String> checkOutCol = new TableColumn<>("Check Out");

        nameCol.setCellValueFactory(cellData ->
                cellData.getValue().employeeNameProperty());

        deptCol.setCellValueFactory(cellData ->
                cellData.getValue().departmentProperty());

        statusCol.setCellValueFactory(cellData ->
                cellData.getValue().statusProperty());

        checkInCol.setCellValueFactory(cellData ->
                cellData.getValue().checkInProperty());

        checkOutCol.setCellValueFactory(cellData ->
                cellData.getValue().checkOutProperty());

        attendance_table.getColumns().addAll(nameCol, deptCol, statusCol, checkInCol, checkOutCol);
        attendance_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void populateTopEmployeeColumn() {
        TableColumn<Top10EmployeeDisplay, String> nameCol = new TableColumn<>("Employee Name");
        TableColumn<Top10EmployeeDisplay, String> deptCol = new TableColumn<>("Department");
        TableColumn<Top10EmployeeDisplay, String> overtimeCol = new TableColumn<>("Overtime Hours");
        TableColumn<Top10EmployeeDisplay, String> bonusCol = new TableColumn<>("Bonus Amount");

// Bind columns to properties
        nameCol.setCellValueFactory(cellData ->
                cellData.getValue().employeeNameProperty());

        deptCol.setCellValueFactory(cellData ->
                cellData.getValue().departmentProperty());

        overtimeCol.setCellValueFactory(cellData ->
                cellData.getValue().overtimeHoursProperty());

        bonusCol.setCellValueFactory(cellData ->
                cellData.getValue().bonusAmountProperty());

// Add to table
        top10_table.getColumns().addAll(nameCol, deptCol, overtimeCol, bonusCol);
        top10_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void populateAttendanceTable() throws Exception {
        ObservableList<AttendanceDisplay> list = FXCollections.observableArrayList(attendanceService.getAttendanceList());
        attendance_table.setItems(list);
    }

    private void populateTopTable() throws Exception {
        ObservableList<Top10EmployeeDisplay> list = FXCollections.observableArrayList(employeeService.getTop10Employee());
        top10_table.setItems(list);
    }

    private void addNewCheckInToTable() throws Exception {
        attendance_table.getItems().addFirst(attendanceService.getNewCheckIn());
    }

    private void checkRecentActivity() {
        Thread checkNewCheckIn = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(2000);
                        RecentActivityDisplay attendanceDisplayFromDb = attendanceService.getFirstAttendance();
                        if (currentDisplay == null) {
                            currentDisplay = attendanceDisplayFromDb;
                            addRecentActivity();
                        } else if (attendanceDisplayFromDb != null) {
                            if (attendanceDisplayFromDb.getId() != currentDisplay.getId()) {
                                currentDisplay = attendanceDisplayFromDb;
                                addNewCheckInToTable();
                                addRecentActivity();
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        checkNewCheckIn.start();
    }

    private void addRecentActivity() {
        javafx.application.Platform.runLater(() -> {
            addActivity(
                    currentDisplay.getEmployeeName(),
                    currentDisplay.getStatus(),
                    currentDisplay.getTimeAgo()
            );
        });
    }

    private RecentActivityDisplay currentDisplay;
    private AttendanceService attendanceService;

    @FXML
    protected void initialize() {
        main_scrollpane.setFitToWidth(true);
        attendanceService = new AttendanceService();
        initChoiceBox();
        if (!ApplicationLanguageSetter.getCurrentLanguage().equals("EN")) {
            translateText();
        }

        first_num_label.setText(String.valueOf(employeeService.getTotalEmployee()));
        second_num_label.setText(String.valueOf(attendanceService.getTotalCheckIn()));
        third_num_label.setText(String.valueOf(attendanceService.getTotalCheckOut()));

        checkRecentActivity();
        Thread populateAttendance = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    populateAttendanceTableColumn();
                    populateAttendanceTable();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread populateEmployee = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    populateTopEmployeeColumn();
                    populateTopTable();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        populateAttendance.start();
        populateEmployee.start();
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
}
