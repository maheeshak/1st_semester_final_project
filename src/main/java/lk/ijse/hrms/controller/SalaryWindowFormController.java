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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.hrms.db.DBConnection;
import lk.ijse.hrms.dto.tm.SalaryTM;
import lk.ijse.hrms.model.SalaryModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static lk.ijse.hrms.util.TimeDate.localDateAndTime;

public class SalaryWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private TableView<SalaryTM> tblSalary;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colMonth;

    @FXML
    private TableColumn<?, ?> colBasic;

    @FXML
    private TableColumn<?, ?> colEarnings;

    @FXML
    private TableColumn<?, ?> colDeductions;

    @FXML
    private TableColumn<?, ?> colNetSalary;

    @FXML
    private JFXButton btnAdd;

    private boolean isSettingButtonClicked;

    @FXML
    private JFXTextField txtSearch;

    ObservableList<SalaryTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localDateAndTime(lblDate, lblTime);
        setCellValueFactory();
        getAll();

    }

    private void getAll() {


        List<SalaryTM> salaryTM = null;
        try {
            salaryTM = SalaryModel.getAll();


            for (SalaryTM salary : salaryTM) {
                obList.add(salary);
            }

            tblSalary.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colBasic.setCellValueFactory(new PropertyValueFactory<>("basic"));
        colEarnings.setCellValueFactory(new PropertyValueFactory<>("earnings"));
        colDeductions.setCellValueFactory(new PropertyValueFactory<>("dedication"));
        colNetSalary.setCellValueFactory(new PropertyValueFactory<>("net_salary"));

    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/paymentSlip_window_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Payment Sheet");
        stage.setResizable(false);
        Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
        stage.getIcons().add(icon);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obList.clear();
                getAll();
            }
        });
        stage.show();
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
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        //set login page when click log out button...
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_window_form.fxml"))));
        stage.centerOnScreen();

    }

    private void searchFilter() {
        FilteredList<SalaryTM> filteredData = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(SalaryTM -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (SalaryTM.getEmp_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (SalaryTM.getEmp_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (SalaryTM.getMonth().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (SalaryTM.getMonth().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<SalaryTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblSalary.comparatorProperty());
        tblSalary.setItems(sortedData);
    }

    @FXML
    void txtSearchOnAction(KeyEvent event) {
        searchFilter();
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/emp_salary.jrxml");
            JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
            JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
