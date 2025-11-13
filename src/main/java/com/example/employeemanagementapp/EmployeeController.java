package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Connection.DatabaseConnection;
import com.example.employeemanagementapp.Entities.Departments;
import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;
import com.example.employeemanagementapp.Service.DepartmentService;
import com.example.employeemanagementapp.Translators.Translator;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    @FXML
    private Button dashbroad_nav_button;
    @FXML
    private Button wages_nav_button;
    @FXML
    private Button project_nav_button;
    @FXML
    private Button employees_nav_button;
    @FXML
    private Button payment_nav_button;
    @FXML
    private Button all_button_filter;
    @FXML
    private Button checked_in_button_filter;
    @FXML
    private Button checked_out_button_filter;

    @FXML
    private ScrollPane main_scrollpane;
    @FXML
    private BorderPane main_borderpane;

    @FXML
    private TextField employeeid;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField position;
    @FXML
    private TextField salary;
    @FXML
    private TextField department_id;


    @FXML
    private ChoiceBox<String> department_filter_box;
    @FXML
    private ChoiceBox<String> language_choice_box;


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

    private DepartmentService departmentService;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    private final EmployeeRepository reposistory;

    public EmployeeController() throws Exception {
        reposistory = (EmployeeRepository) new EmployeeRepository()
                .Mapper(new EmployeeMapper())
                .DatabaseConnection(DatabaseConnection.getConnection())
                .TableName("Employees").build();

        departmentService = new DepartmentService();
    }

    private void initChoiceBox() {
//        List<String> languageList = new ArrayList<>();
//        languageList.add("EN");
//        languageList.add("VN");
//        languageList.add("JP");
//
//        ObservableList<String> observableLanguageItemList = FXCollections.observableArrayList(languageList);
//
//        language_choice_box.setItems(observableLanguageItemList);
//        language_choice_box.setValue(ApplicationLanguageSetter.getCurrentLanguage());
//
//        language_choice_box.getSelectionModel()
//                .selectedItemProperty()
//                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Information");
//                    alert.setHeaderText("Switching language");
//                    alert.setContentText("Please open the app again!");
//                    alert.showAndWait();
//
//                    ApplicationLanguageSetter.setCurrentLanguage(newValue);
//
//                    System.out.println(newValue);
//                    System.exit(0);
//                });
    }

    private void initButtonStyleClass() {
//        dashbroad_nav_button.getStyleClass().add("nav_button_select");
//        all_button_filter.getStyleClass().setAll("button-selected");
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

    @FXML
    public void initialize() {
        initButtonStyleClass();
        initChoiceBox();

        if (!ApplicationLanguageSetter.getCurrentLanguage().equals("EN")) {
            translateText();
        }


        try {
            departmentService.fetchList(10, 1).forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        main_scrollpane.setFitToWidth(true);
    }

    int currentDepartmentPage = 1;

    private List<Departments> list = new ArrayList<>();

    private void manipulateList(int page) {
        try {
            list = departmentService.fetchList(10, page);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void prevDepartment() {
        currentDepartmentPage--;
        manipulateList(currentDepartmentPage);
        for (Departments departments : list) {
            System.out.println(departments.getDepartment_name());
        }
    }

    @FXML
    protected void nextDepartment() {
        currentDepartmentPage++;
        manipulateList(currentDepartmentPage);
        for (Departments departments : list) {
            System.out.println(departments.getDepartment_name());
        }
    }

    private Employee mapEmployee() {
        Employee employee = new Employee.Builder()
                .Employee_id(Integer.parseInt(employeeid.getText()))
                .First_name(first_name.getText())
                .Last_name(last_name.getText())
                .Email(email.getText())
                .Phone(phone.getText())
                .Position(position.getText())
                .Salary(Double.parseDouble(salary.getText()))
                .Hire_date(Date.valueOf(hire_date.getValue()))
                .Department_id(Integer.parseInt(department_id.getText()))
                .build();

        return employee;
    }

    @FXML
    protected void onAttendanceButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("attendance.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            newStage.setTitle("Insert Attendance");
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void openAddEmployeeModal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_employee.fxml"));
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
    @FXML
    protected void onUpdateButtonClick() {
        try {

            reposistory.update(mapEmployee());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onListAllButtonClick() {
        try {
            reposistory.findAll().forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        try {
            reposistory.insert(mapEmployee());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        try {
            Employee employee = new Employee.Builder().build();
            employee.setEmployee_id(Integer.parseInt(employeeid.getText()));

            reposistory.delete(employee);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
