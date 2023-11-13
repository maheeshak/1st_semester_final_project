package lk.ijse.hrms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hrms.util.Animation;
import lk.ijse.hrms.util.Buttons;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static lk.ijse.hrms.util.TimeDate.localDateAndTime;
import static lk.ijse.hrms.util.TimeDate.setGreeting;

public class MainWindowFormController implements Initializable {
    @FXML
    private AnchorPane rootMain;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnDesignations;

    @FXML
    private JFXButton btnAttendance;

    @FXML
    private JFXButton btnLegalCases;

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblGreetings;

    @FXML
    private ImageView wishImageView;
    @FXML
    private JFXButton btnLogOut;

    private boolean isSettingButtonClicked;

    @FXML
    private TextArea txtArea1;

    @FXML
    private TextArea txtArea2;

    @FXML
    private PieChart pieChart;

    @FXML
    private JFXButton btnClient;

    @FXML
    private JFXButton btnSalary;

    @FXML
    private JFXButton btnProjects;


    @FXML
    void btnAdvisoryOnMouseEnterd(MouseEvent event) {
        showTextArea(txtArea1, "Advisory");
        Animation.scalaTransactionText(txtArea1);
    }

    @FXML
    void btnAdvisoryOnMouseExited(MouseEvent event) {
        txtArea1.setVisible(false);
    }

    @FXML
    void btnChemicalOnMouseEnterd(MouseEvent event) {
        showTextArea(txtArea1, "Chemical");
        Animation.scalaTransactionText(txtArea1);
    }

    @FXML
    void btnChemicalOnMouseExited(MouseEvent event) {
        txtArea1.setVisible(false);
    }

    @FXML
    void btnProcessOnMouseEnterd(MouseEvent event) {
        showTextArea(txtArea1, "Process");
        Animation.scalaTransactionText(txtArea1);
    }

    @FXML
    void btnProcessOnMouseExited(MouseEvent event) {
        txtArea1.setVisible(false);
    }

    @FXML
    void btnPolymerOnMouseEnterd(MouseEvent event) {
        showTextArea(txtArea2, "Polymer");
        Animation.scalaTransactionText(txtArea2);
    }

    @FXML
    void btnPolymerOnMouseExited(MouseEvent event) {
        txtArea2.setVisible(false);
    }

    @FXML
    void btnTechnologyOnMouseEnterd(MouseEvent event) {
        showTextArea(txtArea2, "Technology");
        Animation.scalaTransactionText(txtArea2);
    }

    @FXML
    void btnTechnologyOnMouseExited(MouseEvent event) {
        txtArea2.setVisible(false);
    }

    @FXML
    void btnAdministrationOnMouseEnterd(MouseEvent event) {
        showTextArea(txtArea2, "Addministration");
        Animation.scalaTransactionText(txtArea2);
    }

    @FXML
    void btnAddministrationOnMouseExited(MouseEvent event) {
        txtArea2.setVisible(false);
    }


    private void showTextArea(TextArea txtArea1, String btn) {

        switch (btn) {

            case "Advisory":
                txtArea1.setVisible(true);
                txtArea1.setText("Advisory\nDepartment");
                break;

            case "Chemical":
                txtArea1.setVisible(true);
                txtArea1.setText("Chemical\nDepartment");
                break;
            case "Process":
                txtArea1.setVisible(true);
                txtArea1.setText("Raw Rubber\nProcess");
                break;

            case "Polymer":
                txtArea2.setVisible(true);
                txtArea2.setText("Polymer");
                break;
            case "Technology":
                txtArea2.setVisible(true);
                txtArea2.setText("Technology");
                break;
            case "Addministration":
                txtArea2.setVisible(true);
                txtArea2.setText("Addministration");
                break;
        }


    }


    @FXML
    void btnAttendanceOnAction(ActionEvent event) throws IOException {
        /*In here, when the attendance button is clicked, the attendance fxml file is loaded*/

        setStyle(Buttons.ATTENDANCE);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/attendance_window_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(anchorPane);


    }


    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        /*In here, when the dashboard button is clicked, the dashboard fxml file is loaded*/

        setStyle(Buttons.DASHBOARD);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_window_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(anchorPane);


    }


    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        /*In here, when the employee button is clicked, the employee fxml file is loaded*/

        setStyle(Buttons.EMPLOYEES);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/employees_window_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(anchorPane);

    }

    @FXML
    void btnLegalCasesOnAction(ActionEvent event) throws IOException {
        /*In here, when the legal cases button is clicked, the legal cases fxml file is loaded*/

        setStyle(Buttons.LEGAL);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/legal_cases_window_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(anchorPane);

    }


    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        /*When the logout button is clicked, the dashboard closes
        and switches to the login window*/

        Stage stage = (Stage) rootMain.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_window_form.fxml"))));
        stage.centerOnScreen();
    }

    @FXML
    void btnSettingOnAction(ActionEvent event) {
       /* When the setting button is clicked, the logout button is visible.
          When you click it again, he becomes invisible*/

        if (!isSettingButtonClicked) {
            btnLogOut.setVisible(true);
            isSettingButtonClicked = true;
        } else {
            btnLogOut.setVisible(false);
            isSettingButtonClicked = false;
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Setting values for the date and time labels on the dashboard

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_window_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(anchorPane);

    }

    private void setStyle(Buttons btn) {
        /*The button that is clicked is selected and a style is set
        and the style of other buttons are null*/

        switch (btn) {
            case DASHBOARD:
                btnDashboard.setStyle("-fx-background-color:  E9EEFC");
                btnAttendance.setStyle(null);
                btnClient.setStyle(null);
                btnEmployee.setStyle(null);
                btnLegalCases.setStyle(null);
                btnSalary.setStyle(null);
                btnProjects.setStyle(null);
                break;

            case EMPLOYEES:
                btnEmployee.setStyle("-fx-background-color:  E9EEFC");
                btnDashboard.setStyle(null);
                btnClient.setStyle(null);
                btnAttendance.setStyle(null);
                btnLegalCases.setStyle(null);
                btnSalary.setStyle(null);
                btnProjects.setStyle(null);
                break;

            case CLIENT:
                btnClient.setStyle("-fx-background-color:  E9EEFC");
                btnDashboard.setStyle(null);
                btnAttendance.setStyle(null);
                btnEmployee.setStyle(null);
                btnLegalCases.setStyle(null);
                btnSalary.setStyle(null);
                btnProjects.setStyle(null);
                break;

            case ATTENDANCE:
                btnAttendance.setStyle("-fx-background-color:  E9EEFC");
                btnDashboard.setStyle(null);
                btnClient.setStyle(null);
                btnEmployee.setStyle(null);
                btnLegalCases.setStyle(null);
                btnSalary.setStyle(null);
                btnProjects.setStyle(null);
                break;
            case LEGAL:
                btnLegalCases.setStyle("-fx-background-color:  E9EEFC");
                btnDashboard.setStyle(null);
                btnSalary.setStyle(null);
                btnEmployee.setStyle(null);
                btnAttendance.setStyle(null);
                btnClient.setStyle(null);
                btnProjects.setStyle(null);
                break;
            case PROJECT:
                btnProjects.setStyle("-fx-background-color:  E9EEFC");
                btnDashboard.setStyle(null);
                btnSalary.setStyle(null);
                btnEmployee.setStyle(null);
                btnAttendance.setStyle(null);
                btnClient.setStyle(null);
                btnLegalCases.setStyle(null);
                break;
            case SALARY:
                btnSalary.setStyle("-fx-background-color:  E9EEFC");
                btnDashboard.setStyle(null);
                btnProjects.setStyle(null);
                btnEmployee.setStyle(null);
                btnAttendance.setStyle(null);
                btnClient.setStyle(null);
                btnLegalCases.setStyle(null);
                break;


        }
    }


    @FXML
    void btnClientOnAction(ActionEvent event) throws IOException {
        setStyle(Buttons.CLIENT);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/client_window_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(anchorPane);
    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) throws IOException {
        setStyle(Buttons.SALARY);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/salary_window_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(anchorPane);

    }

    @FXML
    void btnProjectsOnAction(ActionEvent event) throws IOException {
        setStyle(Buttons.PROJECT);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/project_window_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(anchorPane);
    }

}