package lk.ijse.hrms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.Observable;
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
import lk.ijse.hrms.model.ClientModel;
import lk.ijse.hrms.model.EmployeeModel;
import lk.ijse.hrms.model.OrderModel;
import lk.ijse.hrms.util.Animation;

import javax.naming.Binding;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static lk.ijse.hrms.util.TimeDate.localDateAndTime;
import static lk.ijse.hrms.util.TimeDate.setGreeting;

public class DashboardWindowFormController implements Initializable {

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
    private PieChart pieChart;
    @FXML
    private Label txtTotalEmployee;

    @FXML
    private Label lblTotalClients;

    @FXML
    private Label lblTotalOrders;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Setting values for the date and time labels on the dashboard

        localDateAndTime(lblDate, lblTime);
        setGreeting(lblGreetings, wishImageView);
        setTotalEmployeeValue();
        setTotalClients();
        setTotalOrderValues();

    }

    private void setTotalOrderValues() {
        String orders_value= "0";
        try {
            orders_value = OrderModel.searchOrderValue();
        } catch (SQLException e) {}
        lblTotalOrders.setText(orders_value);
    }

    private void setTotalClients() {
        String client_value= "0";
        try {
            client_value = ClientModel.searchClientValue();
        } catch (SQLException e) {}
        lblTotalClients.setText(client_value);

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
        }


    }





    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        /*When the logout button is clicked, the dashboard closes
        and switches to the login window*/

        Stage stage = (Stage) root.getScene().getWindow();
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


    private void setTotalEmployeeValue() {
        String emp_value= "0";
        try {
            emp_value = EmployeeModel.searchEmployeeValue();
        } catch (SQLException e) {}
        txtTotalEmployee.setText(emp_value);
    }


}
