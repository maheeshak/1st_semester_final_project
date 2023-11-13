package lk.ijse.hrms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hrms.model.UserModel;
import lk.ijse.hrms.util.Animation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private ImageView imgLogo;

    @FXML
    private TextField txtUserName;


    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> comboBox;


    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userNameText = txtUserName.getText();
        String passwordText = txtPassword.getText();
        int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
        boolean isEmpty = isEmpty(userNameText, passwordText, selectedIndex);

        if (!isEmpty) {
            String selectedItem = comboBox.getSelectionModel().getSelectedItem();


            try {
                //update user table
                boolean isSaved = UserModel.save(userNameText, passwordText, selectedItem);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Registered !!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "User name already exists !!!").show();
            }


        }

    }

    private boolean isEmpty(String userNameText, String passwordText, int selectedIndex) {
        boolean isEmpty = false;

        if (userNameText.equals("")) {
            txtUserName.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            Animation.translateTransition(txtUserName);
            isEmpty = true;

        }
        if (passwordText.equals("")) {
            txtPassword.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            Animation.translateTransition(txtPassword);
            isEmpty = true;

        }

        if (selectedIndex == 0) {
            comboBox.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            Animation.translateTransition(comboBox);
            isEmpty = true;
        }

        return isEmpty;

    }

    @FXML
    void comboBoxOnAction(ActionEvent event) {
        comboBox.setStyle("-fx-border-color:  #9EB7FD ; -fx-border-radius: 2px");
    }

    @FXML
    void passwordFieldONAction(KeyEvent event) {
        txtPassword.setStyle("-fx-border-color:  #9EB7FD ; -fx-border-radius: 2px");
    }


    @FXML
    void userNameFieldOnAction(KeyEvent event) {
        txtUserName.setStyle("-fx-border-color:  #9EB7FD ; -fx-border-radius: 2px");
    }

    @FXML
    void txtSignInOnAction(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_window_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Rubber Research Institute - Login Window");
        stage.centerOnScreen();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] items = {null, "Manager", "Admin"};
        comboBox.getItems().addAll(items);
        Animation.scalaTransaction(imgLogo);
    }
    @FXML
    void txtUserNameOnAction(ActionEvent event) {
txtPassword.requestFocus();
    }

}
