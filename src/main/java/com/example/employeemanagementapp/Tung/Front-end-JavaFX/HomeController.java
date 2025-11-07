

import com.example.crm.model.Employee;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class HomeController {

    @FXML private Label dateLabel;
    @FXML private Label clockLabel;
    @FXML private Label totalEmployeesLabel;
    @FXML private Label checkedInLabel;
    @FXML private Label checkedOutLabel;
    @FXML private Label attendanceRateLabel;

    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<String> departmentChoice;

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableView<Employee> tableCheckedIn;
    @FXML private TableView<Employee> tableCheckedOut;
    @FXML private TableView<Employee> topTable;

    @FXML private ListView<String> recentList;

    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private ObservableList<String> recentActivity = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")));
        datePicker.setValue(LocalDate.now());

        setupMockData();
        setupDepartmentChoice();
        setupClock();
        setupTables();
        updateStats();
        setupRecentActivity();
        updateTopPerformers();
    }

    private void setupMockData() {
        employees.addAll(
            new Employee(1, "Sarah Johnson", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100&h=100&fit=crop",
                    "Engineering", "checked-in", "09:00 AM", null, "Office - Floor 3", 15, 450),
            new Employee(2, "Michael Chen", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop",
                    "Marketing", "checked-in", "08:45 AM", null, "Office - Floor 2", 12, 360),
            new Employee(3, "Emily Rodriguez", "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100&h=100&fit=crop",
                    "Sales", "checked-out", "09:15 AM", "05:30 PM", "Remote", 22, 660),
            // ... add all others similarly (trimmed for brevity)
            new Employee(15, "Isabella White", "https://images.unsplash.com/photo-1502685104226-ee32379fefbe?w=100&h=100&fit=crop",
                    "Engineering", "checked-in", "09:05 AM", null, "Office - Floor 3", 17, 510)
        );
    }

    private void setupDepartmentChoice() {
        Set<String> depts = new LinkedHashSet<>();
        depts.add("All Departments");
        employees.stream().map(Employee::getDepartment).distinct().forEach(depts::add);
        departmentChoice.setItems(FXCollections.observableArrayList(depts));
        departmentChoice.setValue("All Departments");

        departmentChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            applyFilters();
        });
    }

    private void setupClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            clockLabel.setText(java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    private void setupTables() {
        // Employee table columns: custom cell for avatar+name
        TableColumn<Employee, Employee> empCol = (TableColumn<Employee, Employee>) employeeTable.getColumns().get(0);
        empCol.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue()));
        empCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    ImageView iv = new ImageView();
                    iv.setFitWidth(32);
                    iv.setFitHeight(32);
                    try {
                        iv.setImage(new Image(item.getAvatarUrl(), true));
                    } catch (Exception ex) {
                        // fallback
                    }
                    Label name = new Label(item.getName());
                    HBox box = new HBox(8, iv, name);
                    setGraphic(box);
                }
            }
        });

        // The rest columns map to properties
        employeeTable.getColumns().get(1).setCellValueFactory(cell -> cell.getValue().departmentProperty());
        employeeTable.getColumns().get(2).setCellValueFactory(cell -> cell.getValue().statusProperty());
        employeeTable.getColumns().get(3).setCellValueFactory(cell -> cell.getValue().checkInProperty());
        employeeTable.getColumns().get(4).setCellValueFactory(cell -> cell.getValue().checkOutProperty());
        employeeTable.getColumns().get(5).setCellValueFactory(cell -> cell.getValue().locationProperty());

        // checked-in table
        tableCheckedIn.getColumns().get(0).setCellValueFactory(cell -> cell.getValue().nameProperty());
        tableCheckedIn.getColumns().get(1).setCellValueFactory(cell -> cell.getValue().departmentProperty());
        tableCheckedIn.getColumns().get(2).setCellValueFactory(cell -> cell.getValue().checkInProperty());
        tableCheckedIn.getColumns().get(3).setCellValueFactory(cell -> cell.getValue().locationProperty());

        // checked-out table
        tableCheckedOut.getColumns().get(0).setCellValueFactory(cell -> cell.getValue().nameProperty());
        tableCheckedOut.getColumns().get(1).setCellValueFactory(cell -> cell.getValue().departmentProperty());
        tableCheckedOut.getColumns().get(2).setCellValueFactory(cell -> cell.getValue().checkInProperty());
        tableCheckedOut.getColumns().get(3).setCellValueFactory(cell -> cell.getValue().checkOutProperty());

        // top performers table
        topTable.getColumns().get(0).setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(employees.indexOf(cell.getValue()) + 1));
        topTable.getColumns().get(1).setCellValueFactory(cell -> cell.getValue().nameProperty());
        topTable.getColumns().get(2).setCellValueFactory(cell -> cell.getValue().departmentProperty());
        topTable.getColumns().get(3).setCellValueFactory(cell -> cell.getValue().overtimeHoursProperty().asObject());
        topTable.getColumns().get(4).setCellValueFactory(cell -> cell.getValue().bonusAmountProperty().asObject());
        topTable.getColumns().get(5).setCellValueFactory(cell -> cell.getValue().statusProperty());

        applyFilters(); // populate tables initially
    }

    private void applyFilters() {
        String dept = departmentChoice.getValue();
        List<Employee> filtered = employees.stream()
                .filter(e -> dept.equals("All Departments") || e.getDepartment().equals(dept))
                .collect(Collectors.toList());

        employeeTable.setItems(FXCollections.observableArrayList(filtered));
        tableCheckedIn.setItems(FXCollections.observableArrayList(
                filtered.stream().filter(e -> "checked-in".equals(e.getStatus())).collect(Collectors.toList())
        ));
        tableCheckedOut.setItems(FXCollections.observableArrayList(
                filtered.stream().filter(e -> "checked-out".equals(e.getStatus())).collect(Collectors.toList())
        ));

        updateStats();
        updateTopPerformers();
    }

    private void updateStats() {
        int total = employees.size();
        long checkedIn = employees.stream().filter(e -> "checked-in".equals(e.getStatus())).count();

        totalEmployeesLabel.setText(String.valueOf(total));
        checkedInLabel.setText(String.valueOf(checkedIn));
        checkedOutLabel.setText(String.valueOf(total - checkedIn));
        int rate = total == 0 ? 0 : (int) Math.round((checkedIn * 100.0) / total);
        attendanceRateLabel.setText(rate + "%");
    }

    private void setupRecentActivity() {
        recentActivity.addAll(
            "Sarah Johnson — Checked In — 2 minutes ago",
            "Michael Chen — Checked In — 17 minutes ago",
            "Emily Rodriguez — Checked Out — 32 minutes ago",
            "David Park — Checked In — 1 hour ago",
            "Jessica Williams — Checked In — 2 hours ago"
        );
        recentList.setItems(recentActivity);
    }

    private void updateTopPerformers() {
        List<Employee> top = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getOvertimeHours).reversed())
                .limit(10)
                .collect(Collectors.toList());

        topTable.setItems(FXCollections.observableArrayList(top));

        // Format bonus amount in cell factory if needed
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        topTable.getColumns().get(4).setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(nf.format((Double) item));
                }
            }
        });
    }
}

