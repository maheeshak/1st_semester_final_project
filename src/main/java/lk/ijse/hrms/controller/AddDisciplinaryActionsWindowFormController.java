package lk.ijse.hrms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.model.DisciplinaryActionModel;
import lk.ijse.hrms.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddDisciplinaryActionsWindowFormController implements Initializable {
    @FXML
    private TextArea txtDecAction;

    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblDesignation;

    @FXML
    private Label lblDep;
    @FXML
    private ComboBox<String> dicsiplinaryActionID;

    @FXML
    private Label lblDcsiplinaryAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValueEmpID();
        setDicsId();
    }

    private void setDicsId() {
        try {
            List<String> ids = DisciplinaryActionModel.getIds();
            dicsiplinaryActionID.getItems().addAll(ids);
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
    void btnSaveOnAction(ActionEvent event) {
        String empId = cmbEmpId.getSelectionModel().getSelectedItem();
        String disId = dicsiplinaryActionID.getSelectionModel().getSelectedItem();
        try {

            boolean isAdded = DisciplinaryActionModel.add(empId, disId, LocalDate.now());

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Disciplinary action Added !!!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Disciplinary action not Added !!!").show();

            }
        } catch (SQLException e) {

            new Alert(Alert.AlertType.ERROR, "Disciplinary action already Added !!!").show();
        }
    }

    @FXML
    void cmbEmpIdOnAction(ActionEvent event) {
        String id = cmbEmpId.getSelectionModel().getSelectedItem();
        try {
            Employee employee = EmployeeModel.searchById(id);
            lblName.setText(employee.getName());
            lblDep.setText(employee.getDep_id());
            lblDesignation.setText(employee.getDesignation());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void cmbDicsiplinaryActionIDOnAction(ActionEvent event) {
        String disId = dicsiplinaryActionID.getSelectionModel().getSelectedItem();
        try {
            String disAction = DisciplinaryActionModel.serchById(disId);
            lblDcsiplinaryAction.setText(disAction);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
