package lk.ijse.hrms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.hrms.dto.Project;
import lk.ijse.hrms.model.EmployeeModel;
import lk.ijse.hrms.model.ProjectModel;
import lk.ijse.hrms.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AddProjectFormController implements Initializable {
    @FXML
    private TextField txtProjectId;

    @FXML
    private TextField txtProjectName;

    @FXML
    private TextField projectCost;

    @FXML
    private TextField txtProjectManager;

    @FXML
    private TextField projectPhase;

    @FXML
    private TextField projectDuration;
    @FXML
    private TextField txtEmployee1;

    @FXML
    private TextField txtEmployee2;

    @FXML
    private TextField txtEmployee4;

    @FXML
    private TextField txtEmployee3;

    @FXML
    private TextField txtEmployee5;

    @FXML
    private ComboBox<String> cmbEmployee;
    @FXML
    private Label lblCurrentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEmpId();
        setCurrentProjId();

    }

    private void setCurrentProjId() {
        try {

            String current_proj_id = ProjectModel.getCurrentId();
            lblCurrentId.setText(current_proj_id);
        } catch (SQLException e) {

        }


    }

    private void setEmpId() {
        try {
            List<String> ids = EmployeeModel.getIds();
            cmbEmployee.getItems().addAll(ids);

        } catch (SQLException e) {

        }
    }

    @FXML
    void btnSaveOnaction(ActionEvent event) {
        String proj_id = txtProjectId.getText();
        String duration = projectDuration.getText();
        String cost = projectCost.getText();
        String phases = projectPhase.getText();
        String project_name = txtProjectName.getText();
        String proj_man = txtProjectManager.getText();
        String emp1 = txtEmployee1.getText();
        String emp2 = txtEmployee2.getText();
        String emp3 = txtEmployee3.getText();
        String emp4 = txtEmployee4.getText();
        String emp5 = txtEmployee5.getText();
        try {

            boolean isValid = checkValidity();
            if (isValid) {
                boolean isAdded = ProjectModel.add(new Project(proj_id, project_name, duration, proj_man, cost, phases), emp1, emp2, emp3,
                        emp4, emp5);

                if (isAdded) {
                    clearFields();
                    setCurrentProjId();
                    new Alert(Alert.AlertType.CONFIRMATION, "Project Added !!!").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Project not  Added !!!").show();

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtProjectId.setText("");
        txtProjectName.setText("");
        projectCost.setText("");
        projectPhase.setText("");
        txtProjectName.setText("");
        txtProjectManager.setText("");
        txtProjectManager.setText("");
        txtEmployee1.setText("");
        txtEmployee2.setText("");
        txtEmployee3.setText("");
        txtEmployee4.setText("");
        txtEmployee5.setText("");
    }

    private boolean checkValidity() {
        String project_id = txtProjectId.getText();
        String cost = projectCost.getText();
        String phase = projectPhase.getText();

        boolean isValid = true;

        if (!RegexPatterns.getProjectIdPattern().matcher(project_id).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID !!!").show();
            isValid = false;

        } else if (!RegexPatterns.getDoublePattern().matcher(cost).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Cost !!!").show();
            isValid = false;

        }
        else if (!RegexPatterns.getIntPattern().matcher(phase).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid phase !!!").show();
            isValid = false;

        }

        return isValid;
    }

    @FXML
    void cmbEmployeeOnAction(ActionEvent event) {
        if (txtEmployee1.getText().isEmpty()) {
            txtEmployee1.setText(cmbEmployee.getSelectionModel().getSelectedItem());
        } else if (txtEmployee2.getText().isEmpty()) {
            txtEmployee2.setText(cmbEmployee.getSelectionModel().getSelectedItem());
        } else if (txtEmployee3.getText().isEmpty()) {
            txtEmployee3.setText(cmbEmployee.getSelectionModel().getSelectedItem());
        } else if (txtEmployee4.getText().isEmpty()) {
            txtEmployee4.setText(cmbEmployee.getSelectionModel().getSelectedItem());
        } else if (txtEmployee5.getText().isEmpty()) {
            txtEmployee5.setText(cmbEmployee.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    void txtCostOnAction(ActionEvent event) {
        projectPhase.requestFocus();
    }

    @FXML
    void txtDurationOnAction(ActionEvent event) {
        projectCost.requestFocus();
    }

    @FXML
    void txtPhasesOnaction(ActionEvent event) {
        txtProjectName.requestFocus();
    }

    @FXML
    void txtProjectIdOnAction(ActionEvent event) {
        projectDuration.requestFocus();
    }

    @FXML
    void txtProjectMAnagerOnAction(ActionEvent event) {
        cmbEmployee.requestFocus();
    }

    @FXML
    void txtProjectNameOnAction(ActionEvent event) {
        txtProjectManager.requestFocus();
    }

}
