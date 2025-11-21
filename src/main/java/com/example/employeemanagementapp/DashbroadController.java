package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Models.AttendanceDisplay;
import com.example.employeemanagementapp.Service.AttendanceService;
import com.example.employeemanagementapp.Service.EmployeeService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.geometry.Insets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    private AttendanceDisplay currentDisplay = new AttendanceDisplay();
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
        Thread checkNewCheckIn = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        AttendanceDisplay attendanceDisplayFromDb = attendanceService.getFirstAttendance();
                        if (attendanceDisplayFromDb != null) {
                            if (attendanceDisplayFromDb.getId() != currentDisplay.getId()) {
                                currentDisplay = attendanceDisplayFromDb;

                                javafx.application.Platform.runLater(() -> {
                                    addActivity(
                                            currentDisplay.getEmployeeName(),
                                            currentDisplay.getStatus(),
                                            currentDisplay.getTimeAgo()
                                    );
                                });
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        checkNewCheckIn.start();
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
