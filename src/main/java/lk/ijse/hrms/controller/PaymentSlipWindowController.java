package lk.ijse.hrms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.model.EmployeeModel;
import lk.ijse.hrms.model.SalaryModel;
import lk.ijse.hrms.months.Months;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentSlipWindowController implements Initializable {
    @FXML
    private JFXComboBox<String> cmbMonth;

    @FXML
    private JFXComboBox<String> cmbEmployeeId;

    @FXML
    private Label lblSalaryId;

    @FXML
    private Label lblDesignation;

    @FXML
    private Label lblTotalEarnings;

    @FXML
    private Label lblTotalDeductions;

    @FXML
    private Label lblNet;

    @FXML
    private Label lblPaymentDate;

    @FXML
    private Label lblBasic;

    @FXML
    private Label lblGrossSalary;

    @FXML
    private TextField txtmedical;

    @FXML
    private TextField txtfood;

    @FXML
    private TextField txtOt;

    @FXML
    private TextField txtOther;

    @FXML
    private Label lblNetSalary;

    @FXML
    private Label lblTotal;

    @FXML
    private TextField txtLoan;

    @FXML
    private TextField txtInsuarance;

    @FXML
    private Label lblEpf;

    @FXML
    private Label lblEtf;

    @FXML
    private TextField txtLossOfPay;

    @FXML
    private JFXButton btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPaymentMonth();
        setEmpID();
        lblPaymentDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setEmpID() {
        //add values to the combo box by adding those values through an observable list....
        try {
            List<String> empId = EmployeeModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();
            for (String id : empId) {
                obList.add(id);
            }
            cmbEmployeeId.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPaymentMonth() {
        ObservableList<String> months = FXCollections.observableArrayList();
        months.add(String.valueOf(Months.JAN));
        months.add(String.valueOf(Months.FEB));
        months.add(String.valueOf(Months.MAR));
        months.add(String.valueOf(Months.APR));
        months.add(String.valueOf(Months.JUN));
        months.add(String.valueOf(Months.JUL));
        months.add(String.valueOf(Months.AUG));
        months.add(String.valueOf(Months.SEP));
        months.add(String.valueOf(Months.OTC));
        months.add(String.valueOf(Months.NOV));
        months.add(String.valueOf(Months.DEC));

        cmbMonth.setItems(months);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String emp_id = cmbEmployeeId.getSelectionModel().getSelectedItem();
        String sal_id = lblSalaryId.getText();
        Double basic = Double.valueOf(lblBasic.getText());
        Double totalEarnings = Double.valueOf(lblTotalEarnings.getText());
        Double totalDeductions = Double.valueOf(lblTotalDeductions.getText());
        Double netSalary = Double.valueOf(lblNetSalary.getText());
        String month = cmbMonth.getSelectionModel().getSelectedItem();
        try {
            boolean isValid = SalaryModel.check(emp_id, month);
            if (isValid) {
                boolean isAdded = SalaryModel.add(emp_id, sal_id, basic,  totalEarnings, totalDeductions, netSalary, month);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Salary Added !!!").show();

                } else {


                    new Alert(Alert.AlertType.ERROR, "Salary not Added !!!").show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Salary Already Added !!!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void cmbEmployeeIdOnAction(ActionEvent event) {
        String empId = cmbEmployeeId.getSelectionModel().getSelectedItem();
        try {
            Employee employee = EmployeeModel.searchById(empId);
            lblDesignation.setText(employee.getDesignation());

            String designation = employee.getDesignation();
            if (designation.equals("Manager")) {
                lblSalaryId.setText("S001");
            } else {
                lblSalaryId.setText("S002");

            }

            String salId = lblSalaryId.getText();
            lblBasic.setText(SalaryModel.searchById(salId));
            setEtfEpf();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setEtfEpf() {

        Double basic = Double.valueOf(lblBasic.getText());

        Double etf = (basic / 100) * 3;
        Double epf = (basic / 100) * 8;

        lblEtf.setText(String.valueOf(etf));
        lblEpf.setText(String.valueOf(epf));


    }

    @FXML
    void txtFoodAllownessOnAction(ActionEvent event) {
        txtOt.requestFocus();
    }

    @FXML
    void txtMedicalAllownessOnAction(ActionEvent event) {
        txtfood.requestFocus();
    }

    @FXML
    void txtOtOnAction(ActionEvent event) {
        txtOther.requestFocus();
    }

    @FXML
    void txtOtherOnAction(ActionEvent event) {
        calculateGrossSalary();

        txtLossOfPay.requestFocus();
    }

    private void calculateGrossSalary() {
        Double basic = Double.valueOf(lblBasic.getText());
        Double medical = Double.valueOf(txtmedical.getText());
        Double food = Double.valueOf(txtfood.getText());
        Double ot = Double.valueOf(txtOt.getText());
        Double other = Double.valueOf(txtOther.getText());


        Double gross_salary = basic + medical + food + ot + other;
        lblTotalEarnings.setText(String.valueOf(gross_salary));
        lblGrossSalary.setText(String.valueOf(gross_salary));

    }

    @FXML
    void txtInsuaranceOnAction(ActionEvent event) {
        Double insurance = Double.valueOf(txtInsuarance.getText());
        Double lossOfPay = Double.valueOf(txtLossOfPay.getText());
        Double loan = Double.valueOf(txtLoan.getText());
        Double etf = Double.valueOf(lblEtf.getText());
        Double epf = Double.valueOf(lblEpf.getText());

        Double total = insurance + lossOfPay + loan + epf + etf;
        lblTotalDeductions.setText(String.valueOf(total));
        lblTotal.setText(String.valueOf(total));


        Double gross = Double.valueOf(lblGrossSalary.getText());
        Double net_salary = gross - total;
        lblTotalDeductions.setText(String.valueOf(total));
        lblNet.setText(String.valueOf(net_salary));
        lblNetSalary.setText(String.valueOf(net_salary));
    }

    @FXML
    void txtLoanOnaAction(ActionEvent event) {
        txtInsuarance.requestFocus();
    }

    @FXML
    void txtLossOfPayOnaction(ActionEvent event) {
        txtLoan.requestFocus();
    }

}
