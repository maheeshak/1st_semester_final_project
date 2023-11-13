package lk.ijse.hrms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.hrms.dto.Attendance;
import lk.ijse.hrms.dto.AttendanceDetails;
import lk.ijse.hrms.dto.tm.AttendanceTM;
import lk.ijse.hrms.model.AttendanceModel;
import lk.ijse.hrms.model.EmployeeModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static lk.ijse.hrms.util.TimeDate.localDateAndTime;

public class AttendanceWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;


    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXButton btnLogOut;

    private boolean isSettingButtonClicked;

    @FXML
    private TableView<AttendanceTM> tblAttendance;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colDesignation;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private Label lblAttendance;

    @FXML
    private Label lblLate;

    @FXML
    private Label lblNopay;

    Integer[] data = null;

    @FXML
    private JFXTextField txtSearch;

    ObservableList<AttendanceTM> obList = FXCollections.observableArrayList();

    public AttendanceWindowFormController() {

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localDateAndTime(lblDate, lblTime);
        getAll();
        setCellValueFactory();
        setLabelValues();
        setBarGraphValues();
    }

    private void setBarGraphValues() {
        try {
            data = EmployeeModel.getEmployeeValueMonths();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        XYChart.Series<String, Integer> series = new XYChart.Series();
        series.setName("No. of Customers");
        series.getData().add(new XYChart.Data("JAN", data[0]));
        series.getData().add(new XYChart.Data("FEB", data[1]));
        series.getData().add(new XYChart.Data("MAR", data[2]));
        series.getData().add(new XYChart.Data("APR", data[3]));
        series.getData().add(new XYChart.Data("MAY", data[4]));
        series.getData().add(new XYChart.Data("JUN", data[5]));
        series.getData().add(new XYChart.Data("JUL", data[6]));
        series.getData().add(new XYChart.Data("AUG", data[7]));
        series.getData().add(new XYChart.Data("SEP", data[8]));
        series.getData().add(new XYChart.Data("OCT", data[9]));
        series.getData().add(new XYChart.Data("NOV", data[10]));
        series.getData().add(new XYChart.Data("DEC", data[11]));

        barChart.getData().addAll(series);
    }

    private void setLabelValues() {

        try {
            String attdValue = AttendanceModel.getAttendanceValue("On Time","Half Day","Late");
            lblAttendance.setText(attdValue);

            String lateValue = AttendanceModel.setLateValue("Late");
            lblLate.setText(lateValue);


            String absent = AttendanceModel.setAbsValue("No pay");
            lblNopay.setText(absent);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    private void getAll() {

        try {
            List<Attendance> atdList = AttendanceModel.getAll();
            for (Attendance atd : atdList) {
                //create the preview button...

                Button btnPreview = new Button();
                Image previewIcon = new Image(getClass().getResourceAsStream("/assets/eye.png"));
                ImageView previewView = new ImageView(previewIcon);
                previewView.setFitHeight(12);
                previewView.setFitWidth(12);
                previewView.setPreserveRatio(true);
                btnPreview.setGraphic(previewView);
                btnPreview.setCursor(Cursor.HAND);
                btnPreview.setStyle("-fx-background-color:#87CEEB; ");

                setPreviewBtnOnAction(btnPreview); //*set button preview action*/

                obList.add(new AttendanceTM(atd.getEmp_id(), atd.getName(), atd.getDesignation(), atd.getDate(), atd.getStatus(), btnPreview));

            }
            tblAttendance.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPreviewBtnOnAction(Button btnPreview) {
        btnPreview.setOnAction((e) -> {
            int selectedIndex = tblAttendance.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                ObservableList<AttendanceTM> attendance = tblAttendance.getItems();
                AttendanceTM attendanceTM = attendance.get(selectedIndex);
                String id = attendanceTM.getEmp_id();
                String date = attendanceTM.getDate();
                try {
                    AttendanceDetails attendanceDetails = AttendanceModel.preview(id, date);
                    AttendanceShowWindowController.attendance = attendanceDetails;
                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/attendance_show_window.fxml"));
                    Scene scene = new Scene(anchorPane);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Employee Attendance");
                    stage.setResizable(false);
                    Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
                    stage.getIcons().add(icon);

                    stage.show();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        //set login page when click log out button...
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_window_form.fxml"))));
        stage.centerOnScreen();
    }

    @FXML
    void btnSettingOnAction(ActionEvent event) {
        //set the logout button visible and invisible when setting button clicked....
        if (!isSettingButtonClicked) {
            btnLogOut.setVisible(true);
            isSettingButtonClicked = true;
        } else {
            btnLogOut.setVisible(false);
            isSettingButtonClicked = false;
        }
    }

    @FXML
    void btnAttendanceFormOnAction(ActionEvent event)  {
        try {

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/attendance_mark_window_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Attendance Form");
            stage.setResizable(false);
            Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
            stage.getIcons().add(icon);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent e) {
                    obList.clear();
                    getAll();
                    barChart.getData().clear();
                    setBarGraphValues();
                    setLabelValues();
                }
            });
            stage.show();
        }catch (IOException e){}
    }

    private void searchFilter() {
        FilteredList<AttendanceTM> filteredData = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(AttendanceTM -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (AttendanceTM.getEmp_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (AttendanceTM.getEmp_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (AttendanceTM.getName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (AttendanceTM.getName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (AttendanceTM.getDesignation().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (AttendanceTM.getDesignation().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (AttendanceTM.getDate().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (AttendanceTM.getDate().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<AttendanceTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblAttendance.comparatorProperty());
        tblAttendance.setItems(sortedData);
    }


    @FXML
    void txtSearchOnAction(KeyEvent event) {
        searchFilter();
    }
    @FXML
    void btnScanOnAction(ActionEvent event) {
        try {

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/QR_Scan_window_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Attendance Form");
            stage.setResizable(false);
            Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
            stage.getIcons().add(icon);

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent e) {
                    obList.clear();
                    getAll();
                    barChart.getData().clear();
                    setBarGraphValues();
                    setLabelValues();

                }
            });
            stage.show();
        }catch (IOException e){}
    }
    @FXML
    void btnGenerateOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/qr_code_genorator_window_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("QR Generator");
        stage.setResizable(false);
        Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
        stage.getIcons().add(icon);
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}