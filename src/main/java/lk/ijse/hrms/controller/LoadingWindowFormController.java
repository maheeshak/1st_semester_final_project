package lk.ijse.hrms.controller;

import javafx.concurrent.Task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lk.ijse.hrms.util.Animation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingWindowFormController implements Initializable {
    @FXML
    private ImageView imgLogo;

    @FXML
    private ProgressBar prgBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Animation.scalaTransaction(imgLogo);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    updateProgress(i, 55);
                    Thread.sleep(55);
                }
                return null;
            }
        };

        prgBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(event -> {
            try {
                Parent loginParent = FXMLLoader.load(getClass().getResource("/view/login_window_form.fxml"));
                Scene loginScene = new Scene(loginParent);
                Stage loginStage = new Stage();
                loginStage.setResizable(false);
                Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
                loginStage.getIcons().add(icon);
                loginStage.setTitle("Rubber Research Institute - Login");
                loginStage.setScene(loginScene);
                loginStage.show();

                // Close the welcome stage
                ((Stage) prgBar.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        new Thread(task).start();
    }
}
