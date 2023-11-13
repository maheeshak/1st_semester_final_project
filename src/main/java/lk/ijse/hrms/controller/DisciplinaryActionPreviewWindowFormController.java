package lk.ijse.hrms.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.dto.EmployeeDisAction;
import lk.ijse.hrms.model.DisciplinaryActionModel;
import lk.ijse.hrms.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DisciplinaryActionPreviewWindowFormController implements Initializable {
    @FXML
    private Label lblName;

    @FXML
    private Label lblDesignation;

    @FXML
    private Label lblDep;

    @FXML
    private Label lblDcsiplinaryAction;

    @FXML
    private Label txtEmployeeId;

    @FXML
    private Label txtDicActionId;
    @FXML
    private Label txtDate;

    public static EmployeeDisAction employeeDisAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEmpValues();
        setDisValues();
    }

    private void setDisValues() {
        try {
            String descAction = DisciplinaryActionModel.serchById(employeeDisAction.getDis_id());
            txtDicActionId.setText(employeeDisAction.getDis_id());
            lblDcsiplinaryAction.setText(descAction);
            txtDate.setText(employeeDisAction.getDate());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setEmpValues() {
        String id = employeeDisAction.getEmp_id();
        try {
            Employee employee = EmployeeModel.searchById(id);
            txtEmployeeId.setText(id);
            lblName.setText(employee.getName());
            lblDep.setText(employee.getDep_id());
            lblDesignation.setText(employee.getDesignation());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
