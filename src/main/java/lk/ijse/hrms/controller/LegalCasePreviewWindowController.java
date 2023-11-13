package lk.ijse.hrms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.dto.EmployeeLegalCases;
import lk.ijse.hrms.model.EmployeeModel;
import lk.ijse.hrms.model.LegalCaseModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LegalCasePreviewWindowController implements Initializable {

    @FXML
    private Label lblName;

    @FXML
    private Label lblDesignation;

    @FXML
    private Label lblDep;

    @FXML
    private TextField txtLegalCase;

    @FXML
    private TextArea txtLegDesc;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblCaseId;

    public static EmployeeLegalCases employeeLegalCases;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEmpValues();
        setLegValues();

    }

    private void setLegValues() {
        txtLegalCase.setText(employeeLegalCases.getLeg_case());
        txtLegDesc.setText(employeeLegalCases.getLeg_desc());
        lblCaseId.setText(employeeLegalCases.getLeg_id());

    }

    private void setEmpValues() {
        String id = employeeLegalCases.getEmp_id();
        try {
            Employee employee = EmployeeModel.searchById(id);
            lblEmpId.setText(id);
            lblName.setText(employee.getName());
            lblDep.setText(employee.getDep_id());
            lblDesignation.setText(employee.getDesignation());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String leg_id = employeeLegalCases.getLeg_id();
        String leg_case = txtLegalCase.getText();
        String leg_desc = txtLegDesc.getText();
try {

    boolean isUpdated = LegalCaseModel.update(leg_id, leg_case, leg_desc);
    if(isUpdated){
        new Alert(Alert.AlertType.CONFIRMATION,"Legal case updated !!!").show();
    }else {
        new Alert(Alert.AlertType.ERROR,"Legal case not updated !!!").show();

    }
}catch (SQLException e){
    e.printStackTrace();
}


    }
}
