package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Models.ProjectDisplay;
import com.example.employeemanagementapp.Service.ProjectService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class ProjectController {
    @FXML
    private Label main_header_label;
    @FXML
    private Label main_date_label;
    @FXML
    private Label first_card_label;
    @FXML
    private Label second_card_label;
    @FXML
    private Label third_card_label;
    @FXML
    private Label fouth_card_label;
    @FXML
    private Label project_overview_label;
    @FXML
    private Button add_project_button;
    @FXML
    private TableView project_table;
    @FXML
    private ScrollPane main_scrollpane;

    private ProjectService projectService;

    private int currentPage = 1;

    public ProjectController() {
        try {
            projectService = new ProjectService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void openAddProjectModal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_project.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void initialize() {
        main_scrollpane.setFitToWidth(true);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    populateColumn();
                    populateTable();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();

        project_table.setRowFactory(tv -> {
            TableRow<ProjectDisplay> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    ProjectDisplay clicked = row.getItem();
                    openEditPage(clicked);
                }
            });
            return row;
        });

    }

    private void openEditPage(ProjectDisplay project) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_project.fxml"));
            Parent root = loader.load();

            EditProjectController editController = loader.getController();

            editController.initProject(project);
            editController.initInput();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void populateColumn() {
        TableColumn<ProjectDisplay, String> projectNameColumn = new TableColumn<>("Project Name");
        TableColumn<ProjectDisplay, Date> startDateColumn = new TableColumn<>("Start Date");
        TableColumn<ProjectDisplay, Date> endDateColumn = new TableColumn<>("End Date");
        TableColumn<ProjectDisplay, Double> commissionRateColumn = new TableColumn<>("Commission Rate (%)");
        TableColumn<ProjectDisplay, String> numberEmployeeColumn = new TableColumn<>("Employees");
        TableColumn<ProjectDisplay, Double> revenueColumn = new TableColumn<>("Est. Revenue");
        TableColumn<ProjectDisplay, Double> totalEarningColumn = new TableColumn<>("Total Earnings");
        TableColumn<ProjectDisplay, String> statusColumn = new TableColumn<>("Status");

        projectNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProjectName()));
        startDateColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStartDate()));
        endDateColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEndDate()));
        commissionRateColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().commissionRateProperty()).asObject());
        numberEmployeeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNumberOfEmployee()));
        revenueColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getRevenue()).asObject());
        totalEarningColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getTotalEarnings()).asObject());
        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));

        project_table.getColumns().addAll(projectNameColumn, startDateColumn, endDateColumn, commissionRateColumn, numberEmployeeColumn, revenueColumn, totalEarningColumn, statusColumn);
        project_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    }

    private void populateTable() throws Exception{
        ObservableList<ProjectDisplay> list = FXCollections.observableArrayList(projectService.fetchList(10, currentPage));
        project_table.setItems(list);
    }

    @FXML
    protected void prev() {
        currentPage--;
        try {
            if (currentPage != 0) {
                populateTable();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void next() {
        currentPage++;
        try {
            populateTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
