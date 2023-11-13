package lk.ijse.hrms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.hrms.dto.EmployeeDisAction;
import lk.ijse.hrms.dto.EmployeeLegalCases;
import lk.ijse.hrms.dto.tm.DisActionTM;
import lk.ijse.hrms.dto.tm.LegalCaseTM;
import lk.ijse.hrms.model.DisciplinaryActionModel;
import lk.ijse.hrms.model.LegalCaseModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static lk.ijse.hrms.util.TimeDate.localDateAndTime;

public class LegalCasesWindowController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private ImageView imgSetting;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private TableView<DisActionTM> tblDisAction;

    @FXML
    private TableColumn<?, ?> colEmpID;

    @FXML
    private TableColumn<?, ?> colDisId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPreview;
    @FXML
    private TableColumn<?, ?> colRemove;
    @FXML
    private TableView<LegalCaseTM> tblLegCase;

    @FXML
    private TableColumn<?, ?> colLegEmpID;

    @FXML
    private TableColumn<?, ?> colLegCases;

    @FXML
    private TableColumn<?, ?> colLegDate;

    @FXML
    private TableColumn<?, ?> colLegPreview;

    @FXML
    private TableColumn<?, ?> colLegRemove;

    ObservableList<DisActionTM> obDisList = FXCollections.observableArrayList();

    ObservableList<LegalCaseTM> obLegList = FXCollections.observableArrayList();
    @FXML
    private Pane paneAdd;
    private boolean isSettingButtonClicked;
    private boolean isAddButtonClicked;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localDateAndTime(lblDate, lblTime);
        getDisAll();
        setDisCellValueFactory();

        getLegAll();
        setLegCellValueFactory();
    }

    private void setLegCellValueFactory() {
        colLegEmpID.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colLegCases.setCellValueFactory(new PropertyValueFactory<>("leg_case"));
        colLegDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colLegPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        colLegRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void getLegAll() {

        try {

            List<EmployeeLegalCases> leg_Case = LegalCaseModel.getAll();

            for (EmployeeLegalCases employeeLegalCases : leg_Case) {
                Button btnRemove = new Button();
                Image deleteIcon = new Image(getClass().getResourceAsStream("/assets/remove_logo.png"));
                ImageView deleteView = new ImageView(deleteIcon);
                deleteView.setFitHeight(10);
                deleteView.setFitWidth(10);
                deleteView.setPreserveRatio(true);
                btnRemove.setGraphic(deleteView);
                btnRemove.setCursor(Cursor.HAND);
                btnRemove.setStyle("-fx-background-color:  #ff6666; ");

                setLegRemoveBtnOnAction(btnRemove); //*set button remove action*/


                Button btnPreview = new Button();
                Image previewIcon = new Image(getClass().getResourceAsStream("/assets/eye.png"));
                ImageView previewView = new ImageView(previewIcon);
                previewView.setFitHeight(10);
                previewView.setFitWidth(10);
                previewView.setPreserveRatio(true);
                btnPreview.setGraphic(previewView);
                btnPreview.setCursor(Cursor.HAND);
                btnPreview.setStyle("-fx-background-color:#87CEEB; ");

                setLegPreviewBtnOnAction(btnPreview); //*set button preview action*/
                obLegList.add(new LegalCaseTM(employeeLegalCases.getEmp_id(), employeeLegalCases.getLeg_case(), employeeLegalCases.getDate(),
                        btnPreview, btnRemove));

            }
            tblLegCase.setItems(obLegList);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setLegPreviewBtnOnAction(Button btnPreview) {
        btnPreview.setOnAction((e) -> {
            int selectedIndex = tblLegCase.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {


                ObservableList<LegalCaseTM> legalCases = tblLegCase.getItems();
                LegalCaseTM legalCaseTM = legalCases.get(selectedIndex);
                String emp_id = legalCaseTM.getEmp_id();
                String leg_id = legalCaseTM.getLeg_case();

                try {

                    LegalCasePreviewWindowController.employeeLegalCases= LegalCaseModel.find(emp_id,leg_id);

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/legal_case_preview_window.fxml"));

                    try {
                        Scene scene = new Scene(fxmlLoader.load());

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Disciplinary Action Form");
                        Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
                        stage.getIcons().add(icon);
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });


    }

    private void setLegRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int selectedIndex = tblLegCase.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {


                    ObservableList<LegalCaseTM> items = tblLegCase.getItems();
                    LegalCaseTM legalCaseTM = items.get(selectedIndex);
                    String emp_id = legalCaseTM.getEmp_id();

                    boolean isRemove = false;
                    try {
                        isRemove = LegalCaseModel.remove(emp_id);

                        if (isRemove) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted !!!").show();
                            obLegList.clear();
                            getLegAll();
                        } else {
                            new Alert(Alert.AlertType.CONFIRMATION, "oops something went wrong !!!").show();
                        }
                    } catch (SQLException ex) {
                        new Alert(Alert.AlertType.CONFIRMATION, "SQL Error !!!").show();
                    }


                }
            }

        });

    }

    private void getDisAll() {

        try {

            List<EmployeeDisAction> disActions = DisciplinaryActionModel.getAll();

            for (EmployeeDisAction employeeDisAction : disActions) {
                Button btnRemove = new Button();
                Image deleteIcon = new Image(getClass().getResourceAsStream("/assets/remove_logo.png"));
                ImageView deleteView = new ImageView(deleteIcon);
                deleteView.setFitHeight(10);
                deleteView.setFitWidth(10);
                deleteView.setPreserveRatio(true);
                btnRemove.setGraphic(deleteView);
                btnRemove.setCursor(Cursor.HAND);
                btnRemove.setStyle("-fx-background-color:  #ff6666; ");

                setDisRemoveBtnOnAction(btnRemove); //*set button remove action*/


                Button btnPreview = new Button();
                Image previewIcon = new Image(getClass().getResourceAsStream("/assets/eye.png"));
                ImageView previewView = new ImageView(previewIcon);
                previewView.setFitHeight(10);
                previewView.setFitWidth(10);
                previewView.setPreserveRatio(true);
                btnPreview.setGraphic(previewView);
                btnPreview.setCursor(Cursor.HAND);
                btnPreview.setStyle("-fx-background-color:#87CEEB; ");

                 setDisPreviewBtnOnAction(btnPreview); //*set button preview action*/
                obDisList.add(new DisActionTM(employeeDisAction.getDis_id(),
                        employeeDisAction.getEmp_id(),
                        employeeDisAction.getDate(), btnPreview, btnRemove));

            }
            tblDisAction.setItems(obDisList);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setDisPreviewBtnOnAction(Button btnPreview) {
        btnPreview.setOnAction((e) -> {
            int selectedIndex = tblDisAction.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {


                ObservableList<DisActionTM> disAction = tblDisAction.getItems();
                DisActionTM disActionTM = disAction.get(selectedIndex);
                String emp_id = disActionTM.getEmp_id();
                String disc_id = disActionTM.getDis_action();

                try {

                    DisciplinaryActionPreviewWindowFormController.employeeDisAction= DisciplinaryActionModel.find(emp_id,disc_id);

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/disciplinary_action_preview_window_form.fxml"));

                    try {
                        Scene scene = new Scene(fxmlLoader.load());

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Disciplinary Action Form");
                        Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
                        stage.getIcons().add(icon);
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }

    private void setDisRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int selectedIndex = tblDisAction.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {


                    ObservableList<DisActionTM> items = tblDisAction.getItems();
                    DisActionTM disActionTM = items.get(selectedIndex);
                    String emp_id = disActionTM.getEmp_id();
                    String dis_action = disActionTM.getDis_action();

                    boolean isRemove = false;
                    try {
                        isRemove = DisciplinaryActionModel.remove(emp_id,dis_action);

                        if (isRemove) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted !!!").show();
                            obDisList.clear();
                            getDisAll();
                        } else {
                            new Alert(Alert.AlertType.CONFIRMATION, "oops something went wrong !!!").show();
                        }
                    } catch (SQLException ex) {
                        new Alert(Alert.AlertType.CONFIRMATION, "SQL Error !!!").show();
                    }


                }
            }

        });

    }

    private void setDisCellValueFactory() {
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colDisId.setCellValueFactory(new PropertyValueFactory<>("dis_action"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        //set login page when click log out button...
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_window_form.fxml"))));
        stage.centerOnScreen();
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
    void btnAddOnAction(ActionEvent event) {
        if (!isAddButtonClicked) {
            paneAdd.setVisible(true);
            isAddButtonClicked = true;
        } else {
            paneAdd.setVisible(false);
            isAddButtonClicked = false;
        }
    }

    @FXML
    void btnDisciplinaryOnAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/add_disciplinary_actions_window_form.fxml"));
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Disciplinary Action");
        stage.setResizable(false);
        Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
        stage.getIcons().add(icon);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obDisList.clear();
                getDisAll();
            }
        });
        stage.show();
    }

    @FXML
    void btnLegalCaseOnAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/add_legal_case_window_form.fxml"));
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Disciplinary Action");
        stage.setResizable(false);
        Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
        stage.getIcons().add(icon);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obLegList.clear();
                getLegAll();
            }
        });
        stage.show();
    }
}
