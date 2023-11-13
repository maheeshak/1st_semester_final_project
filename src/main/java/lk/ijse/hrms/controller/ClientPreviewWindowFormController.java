package lk.ijse.hrms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.hrms.dto.Client;
import lk.ijse.hrms.model.ClientModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientPreviewWindowFormController implements Initializable {
    @FXML
    private TextField txtClientId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    public static Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValues();
    }

    private void setValues() {
        txtClientId.setText(client.getClient_id());
        txtName.setText(client.getName());
        txtAddress.setText(client.getAddress());
        txtContact.setText(client.getContact());
        txtEmail.setText(client.getEmail());

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtClientId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        try {


            boolean isUpdated = ClientModel.update(new Client(id, name, address, contact, email));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Client is updated !!!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Client is not updated !!!").show();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
