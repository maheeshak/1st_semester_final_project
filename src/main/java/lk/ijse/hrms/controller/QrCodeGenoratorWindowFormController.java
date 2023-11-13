package lk.ijse.hrms.controller;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import lk.ijse.hrms.qr.QrCodeGenerator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QrCodeGenoratorWindowFormController implements Initializable {

    @FXML
    private TextField textFeild;

    @FXML
    private JFXButton btnGen;

    @FXML
    private ImageView pic;

    @FXML
    private JFXButton btnClear;

    @FXML
    void onAction(ActionEvent event) {
        if (!textFeild.getText().isEmpty()) {
            QrCodeGenerator qrGenerator = new QrCodeGenerator();
            qrGenerator.setData(textFeild.getText());
            try {
                qrGenerator.getGenerator();
            } catch (IOException | WriterException e) {
                new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
            }
            File file = new File(qrGenerator.getPath());
            Image image = new Image(file.toURI().toString());
            pic.setImage(image);
        } else {
            new Alert(Alert.AlertType.ERROR, "Input Data First! ").show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        textFeild.clear();
        btnController(true);
        pic.setImage(new Image(new File("src/asserts/9358350_4152212.jpg").toURI().toString()));
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        if (!textFeild.getText().isEmpty()) {
            btnController(false);
        } else {
            btnController(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnController(true);
    }

    void btnController(boolean flag) {
        btnGen.setDisable(flag);
        btnClear.setDisable(flag);
    }
}
