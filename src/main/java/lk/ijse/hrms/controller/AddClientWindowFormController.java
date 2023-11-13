package lk.ijse.hrms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.hrms.dto.Client;
import lk.ijse.hrms.model.ClientModel;
import lk.ijse.hrms.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddClientWindowFormController implements Initializable {
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


    @FXML
    private JFXButton btnSave;
    @FXML
    private Label lblCurrentClientId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setClientId();
    }

    private void setClientId() {

        try {
            String client_id = ClientModel.getCurrentId();
            lblCurrentClientId.setText(client_id);
        } catch (SQLException e) {

        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtClientId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();

        boolean isEmpty = checkEmpty(id, name, address, contact, email);

        if (!isEmpty) {
            try {

                boolean isValid = checkValidity();
                if (isValid) {
                    boolean isSaved = ClientModel.save(new Client(id, name, address, contact, email));
                    if (isSaved) {
                        clearValues();
                        new Alert(Alert.AlertType.CONFIRMATION, "Client Added Successful !!!").show();
                        String currentId = ClientModel.getCurrentId();
                        lblCurrentClientId.setText(currentId);

                    } else {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong !!!").show();
                    }
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Oppps... SQL Error  !!!").show();
            }


        }


    }

    private boolean checkValidity() {
        String client_id = txtClientId.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();

        boolean isValid = true;

        if (!RegexPatterns.getClientIdPattern().matcher(client_id).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid client ID !!!").show();
            isValid = false;
        } else if (!RegexPatterns.getMobilePattern().matcher(contact).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact !!!").show();
            isValid = false;
        } else if (!RegexPatterns.getEmailPattern().matcher(email).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email !!!").show();
            isValid = false;
        }
        return isValid;

    }

    private void clearValues() {
        txtClientId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtEmail.setText("");
    }

    private boolean checkEmpty(String id, String name, String address, String contact, String email) {
        boolean isEmpty = false;
        if (id.equals("")) {
            txtClientId.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;

        } else {
            txtClientId.setStyle(null);

        }

        if (name.equals("")) {
            txtName.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;

        } else {
            txtName.setStyle(null);

        }

        if (address.equals("")) {
            txtAddress.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;

        } else {
            txtAddress.setStyle(null);

        }

        if (contact.equals("")) {

            txtContact.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            txtContact.setStyle(null);

        }
        return isEmpty;
    }

    @FXML
    void txtClientAddressOnAction(ActionEvent event) {
        txtContact.requestFocus();
    }

    @FXML
    void txtClientContatctOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }


    @FXML
    void txtClientIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtClientNameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }
}
