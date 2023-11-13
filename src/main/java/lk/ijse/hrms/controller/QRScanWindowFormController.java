package lk.ijse.hrms.controller;

import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hrms.model.AttendanceModel;
import lk.ijse.hrms.util.TimeDate;
import lk.ijse.hrms.webcam.WebCamView;
import lk.ijse.hrms.webcam.WebcamService;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class QRScanWindowFormController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView imageView;

    @FXML
    private ProgressBar progress;

    @FXML
    private Label lblEmpID;

    @FXML
    private JFXButton btnStart;

    @FXML
    private JFXButton btnStop;
    @FXML
    private Label lblAtdType;

    private WebcamService service;

    @FXML
    private Label lblTime;

    @FXML
    void startBtnOnAction(ActionEvent event) {
        getService().restart();
        progress.setVisible(true);
        btnStart.setDisable(true);
    }

    @FXML
    void stopBtnOnAction(ActionEvent event) {
        getService().cancel();
        progress.setVisible(false);
        btnStart.setDisable(false);
        setGif();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TimeDate.localTime(lblTime);
        setGif();
        progress.setVisible(false);
        Webcam cam = Webcam.getWebcams().get(0);
        service = new WebcamService(cam);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebCamView view = new WebCamView(getService(), imageView);
        pane.getChildren().add(view.getView());
        progress.progressProperty().bind(getService().progressProperty());

        getService().messageProperty().addListener((a, old, c) -> {
            if (c != null) {
                if (old == null) {
                    System.out.println(c);
                } else if (!old.equals(c)) {
                    lblEmpID.setText(c);
                    try {
                        boolean isvalid = AttendanceModel.checkValid(lblEmpID.getText(), String.valueOf(LocalDate.now()));
                        if (isvalid) {
                            setAttendanceType();
                            boolean isAdded = AttendanceModel.add(lblEmpID.getText(), String.valueOf(LocalDate.now()), String.valueOf(lblTime.getText()), lblAtdType.getText());

                            if (isAdded) {

                                new Alert(Alert.AlertType.CONFIRMATION, "Mark Attendance !!!").show();
                            } else {

                                new Alert(Alert.AlertType.ERROR, "Not Mark Attendance !!!").show();

                            }

                        } else {

                            new Alert(Alert.AlertType.ERROR, "Already Mark Attendance !!!").show();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setGif() {
        Image image = new Image(new File("src/main/resources/assest/qr_code.gif").toURI().toString());
        imageView.setImage(image);
    }

    public WebcamService getService() {
        return service;
    }

    private void setAttendanceType() {
        Calendar c = Calendar.getInstance();
        LocalDate now = LocalDate.now();
        c.setTime(new Date());
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        //set image and label....
        if (hours >= 0 && hours < 8) {
            lblAtdType.setText("On Time");
            lblAtdType.setStyle("-fx-text-fill: Green");
        } else if (hours >= 8 && hours <= 12) {
            lblAtdType.setText("Late");
            lblAtdType.setStyle("-fx-text-fill: Red");
        } else if (hours >= 12 && hours < 15) {
            lblAtdType.setText("Half Day");
            lblAtdType.setStyle("-fx-text-fill: Yellow");
        } else {
            lblAtdType.setText("No pay");
            lblAtdType.setStyle("-fx-text-fill: Red");
        }

    }
}



