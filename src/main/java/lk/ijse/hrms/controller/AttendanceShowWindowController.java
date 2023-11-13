package lk.ijse.hrms.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lk.ijse.hrms.dto.AttendanceDetails;

import java.net.URL;
import java.util.ResourceBundle;

public class AttendanceShowWindowController implements Initializable {
    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblAtdType;


    public static AttendanceDetails attendance;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValues();
    }

    private void setValues() {
        lblEmpId.setText(attendance.getEmp_id());
        lblName.setText(attendance.getName());
        lblDate.setText(attendance.getDate());
        lblTime.setText(attendance.getTime());
        lblAtdType.setText(attendance.getStatus());
    }
}
