package lk.ijse.hrms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.model.AttendanceModel;
import lk.ijse.hrms.model.EmployeeModel;
import lk.ijse.hrms.util.TimeDate;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;



public class AttendanceMarkWindowFormController implements Initializable {
    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblAtdType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEmployeeId();
        lblDate.setText(String.valueOf(LocalDate.now()));
        TimeDate.localTime(lblTime);

  /*      lblTime.setText(8);*/
    }

    private void setEmployeeId() {
        //add values to the combo box by adding those values through an observable list....
        try {
            List<String> empId= EmployeeModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();
            for(String id:empId){
                obList.add(id);
            }
            cmbEmpId.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String emp_id = cmbEmpId.getSelectionModel().getSelectedItem();
        String date = lblDate.getText();
        String time = lblTime.getText();
        String attd_type = lblAtdType.getText();

        try {
            boolean isvalid = AttendanceModel.checkValid(emp_id,date);

            if(isvalid){
               boolean isAdded = AttendanceModel.add(emp_id,date,time,attd_type);

               if (isAdded){
                   new Alert(Alert.AlertType.CONFIRMATION,"Mark Attendance !!!").show();
               }else {
                   new Alert(Alert.AlertType.ERROR,"Not Mark Attendance !!!").show();

               }

            }else {
                new Alert(Alert.AlertType.ERROR,"Already Mark Attendance !!!").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void cmbEmpIdOnAction(ActionEvent event) {
        String empId = cmbEmpId.getSelectionModel().getSelectedItem();
        try {
            Employee employee = EmployeeModel.searchById(empId);
            lblName.setText(employee.getName());
            setAttendanceType();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setAttendanceType() {
        Calendar c = Calendar.getInstance();
        LocalDate now = LocalDate.now();
        c.setTime(new Date());
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        //set image and label....
        if (hours >= 0 && hours < 8) {
           lblAtdType.setText("On Time");
           lblAtdType.setStyle("-fx-text-fill: Green");
        } else if (hours >= 8 && hours <= 12) {
            lblAtdType.setText("Late");
            lblAtdType.setStyle("-fx-text-fill: Red");
        }else if(hours >= 12 && hours < 15) {
            lblAtdType.setText("Half Day");
            lblAtdType.setStyle("-fx-text-fill: Yellow");
        }else {
            lblAtdType.setText("No pay");
            lblAtdType.setStyle("-fx-text-fill: Red");
        }
    }
}
