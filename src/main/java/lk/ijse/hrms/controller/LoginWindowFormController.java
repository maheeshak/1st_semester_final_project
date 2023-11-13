package lk.ijse.hrms.controller;

import com.google.protobuf.StringValue;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hrms.model.UserModel;
import lk.ijse.hrms.smtp.Mail;
import lk.ijse.hrms.util.Animation;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;


public class LoginWindowFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private ImageView imgLogo;
    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtOtp;

    @FXML
    private TextField txtEmail;

    @FXML
    private JFXButton btnOtp;

    private int otp;
    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException, MessagingException {
        String userNameText = txtUserName.getText();
        String passwordText = txtPassword.getText();
        String otpCode = txtOtp.getText();
        String email = txtEmail.getText();


        //Check text fields are empty
        boolean isEmpty = isEmpty(userNameText, passwordText,otpCode,email);

        if (!(isEmpty)) {
            if (otp==Integer.valueOf(otpCode)) {
                try {
                    boolean isValid = UserModel.check(userNameText, passwordText);
                    if (isValid) {
                        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_window_form.fxml"));
                        Scene scene = new Scene(anchorPane);

                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("Rubber Research Institute");
                        stage.centerOnScreen();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Incorrect User name or Password !!!").show();
                    }

                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Incorrect User name or Password !!!").show();
                }

            }else {
                new Alert(Alert.AlertType.ERROR,"Incorrect OTP !!!").show();
            }
        }

    }
    @FXML
    void btnOtpOnAction(ActionEvent event) {
        Random random =new Random();
        otp=random.nextInt(10000);

        String email = txtEmail.getText();

        Mail mail = new Mail(); //creating an instance of Mail class
        mail.setMsg("Welcome to Rubber Research Institute.\n\nFor your first login you'll need the OTP.\nYour OTP is :" +otp +"\n" +
                "\nTime : " +
                Time.valueOf(LocalTime.now()) +"\n" +
                "Date : "+
                Date.valueOf(LocalDate.now()));//email message
        mail.setTo(email); //receiver's mail
        mail.setSubject("Alert"); //email subject


        //avoid the  struck of the email...
        Thread thread = new Thread(mail);
        thread.start();
    }

    @FXML
    void txtSignUpOnAction(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/register_window_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Rubber Research Institute - Register Window");
        stage.centerOnScreen();

    }

    @FXML
    void txtPasswordOnKeyTyped(KeyEvent event) {
        txtPassword.setStyle("-fx-border-color:  #9EB7FD ; -fx-border-radius: 2px");
    }


    @FXML
    void txtEmailOnKeyTyped(KeyEvent event) {
        if (!txtEmail.getText().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setStyle("-fx-border-color: red ; -fx-border-radius : 2px");
            btnOtp.setDisable(true);
        } else {
            txtEmail.setStyle("-fx-border-color:  #9EB7FD ; -fx-border-radius: 2px");
            btnOtp.setDisable(false);

        }
    }

    private boolean isEmpty(String userNameText, String passwordText, String otpText, String emailText) {

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
        if (otpText.equals("")) {
            txtOtp.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            Animation.translateTransition(txtOtp);
            isEmpty = true;
        }
        if (emailText.equals("")) {
            txtEmail.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            Animation.translateTransition(txtEmail);
            isEmpty = true;
        }
        return isEmpty;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //logo animation
        Animation.scalaTransaction(imgLogo);
    }


    public void txtUserNameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtOtpOnAction(ActionEvent actionEvent) {
        btnLogin.fire();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    public void txtEmailOnAction(ActionEvent actionEvent) {
        txtOtp.requestFocus();
    }
}
