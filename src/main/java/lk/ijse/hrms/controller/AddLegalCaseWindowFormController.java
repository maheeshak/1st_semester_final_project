package lk.ijse.hrms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.model.EmployeeModel;
import lk.ijse.hrms.model.LegalCaseModel;
import lk.ijse.hrms.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AddLegalCaseWindowFormController implements Initializable {
    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblDesignation;

    @FXML
    private Label lblDep;

    @FXML
    private TextField txtLegalId;

    @FXML
    private TextField txtLegalCase;

    @FXML
    private TextArea txtLegDesc;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValueEmpID();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String empID = cmbEmpId.getSelectionModel().getSelectedItem();
        String legId = txtLegalId.getText();
        String legCase = txtLegalCase.getText();
        String desc = txtLegDesc.getText();
        try {
            boolean isValid = checkValidity();
            if (isValid) {
                boolean isAdded = LegalCaseModel.add(empID, legCase, legId, desc);

                if (isAdded) {
                    clearFields();
                    new Alert(Alert.AlertType.CONFIRMATION, "Legal Case Added !!!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Legal Case not Added !!!").show();

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void clearFields() {
        txtLegalId.setText("");
        txtLegalCase.setText("");
        txtLegDesc.setText("");
    }

    private boolean checkValidity() {
        boolean isValid=true;
        String leg_id = txtLegalId.getText();

        if(!RegexPatterns.getLegIdPattern().matcher(leg_id).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Legal Case ID !!!").show();
            return false;
        }


        return isValid;
    }

    @FXML
    void cmbEmpIdOnAction(ActionEvent event) {
        String id = cmbEmpId.getSelectionModel().getSelectedItem();
        try {
            Employee employee = EmployeeModel.searchById(id);
            lblName.setText(employee.getName());
            lblDep.setText(employee.getDep_id());
            lblDesignation.setText(employee.getDesignation());
            txtLegalId.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setValueEmpID() {
        try {
            List<String> ids = EmployeeModel.getIds();
            cmbEmpId.getItems().addAll(ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtLegalCaseIdOnAction(ActionEvent event) {
        txtLegalCase.requestFocus();
    }

    @FXML
    void txtLegalCaseOnAction(ActionEvent event) {
        txtLegDesc.requestFocus();
    }


}
