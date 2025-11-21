package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Mapper.EmployeeMapper;
import com.example.employeemanagementapp.Repositories.EmployeeRepository;
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
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainController {
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
    private ChoiceBox<String> language_choice_box;

    @FXML
    private BorderPane app_borderpane;

    private Translator translator = ApplicationLanguageSetter.getTranslator();

    @FXML
    private Text header_title_label;

    public MainController() throws Exception {

    }

    private Button prev_nav_button;

    private void clearNavButton() {
        dashbroad_nav_button.getStyleClass().setAll("button", "nav_button");
        wages_nav_button.getStyleClass().setAll("button", "nav_button");
        project_nav_button.getStyleClass().setAll("button", "nav_button");
        employees_nav_button.getStyleClass().setAll("button", "nav_button");
        payment_nav_button.getStyleClass().setAll("button", "nav_button");
    }

    private void initChoiceBox() {
        List<String> languageList = new ArrayList<>();
        languageList.add("EN");
        languageList.add("VN");
        languageList.add("JP");

        ObservableList<String> observableLanguageItemList = FXCollections.observableArrayList(languageList);

        language_choice_box.setItems(observableLanguageItemList);
        language_choice_box.setValue(ApplicationLanguageSetter.getCurrentLanguage());

        language_choice_box.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Switching language");
                    alert.setContentText("Please open the app again!");
                    alert.showAndWait();

                    ApplicationLanguageSetter.setCurrentLanguage(newValue);

                    System.out.println(newValue);
                    System.exit(0);
                });
    }

    private void initButtonStyleClass() {
        clearNavButton();
        dashbroad_nav_button.getStyleClass().add("nav_button_select");
//        all_button_filter.getStyleClass().setAll("button-selected");
    }

    private void translateText() {
        header_title_label.setFont(new Font("Noto Sans CJK JP", header_title_label.getFont().getSize()));
        header_title_label.setText(translator.translate(header_title_label.getText()));
        dashbroad_nav_button.setText(translator.translate(dashbroad_nav_button.getText()));
        wages_nav_button.setText(translator.translate(wages_nav_button.getText()));
        project_nav_button.setText(translator.translate(project_nav_button.getText()));
        employees_nav_button.setText(translator.translate(employees_nav_button.getText()));
        payment_nav_button.setText(translator.translate(payment_nav_button.getText()));
    }

    @FXML
    public void initialize() {
        initButtonStyleClass();
        initChoiceBox();
        try {
            navigate(dashbroad_nav_button, "dashbroad.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!ApplicationLanguageSetter.getCurrentLanguage().equals("EN")) {
            translateText();
        }

    }

    private void navigate(Button nextButton, String filename) throws IOException {
        clearNavButton();
        nextButton.getStyleClass().add("nav_button_select");


        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        Parent root = loader.load();
        app_borderpane.setCenter(root);

        if (root instanceof Region region) {
            region.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            region.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            region.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            region.prefWidthProperty().bind(app_borderpane.widthProperty());
            region.prefHeightProperty().bind(app_borderpane.heightProperty());
        }
    }

    @FXML
    protected void onEmployeeNavButtonClick(ActionEvent event) {
        try {
            navigate(employees_nav_button ,"employee_management.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onProjectButtonClick(ActionEvent event) {
        try {
            navigate(project_nav_button, "project_management.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onDashBoardButtonClick(ActionEvent event) {
        try {
            navigate(dashbroad_nav_button, "dashbroad.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onWagesNavButtonClick(ActionEvent event) {
        try {
            navigate(wages_nav_button, "wage_perfomance_management.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
