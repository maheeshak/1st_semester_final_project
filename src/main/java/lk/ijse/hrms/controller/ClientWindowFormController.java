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
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.hrms.db.DBConnection;
import lk.ijse.hrms.dto.Client;
import lk.ijse.hrms.dto.tm.ClientTM;
import lk.ijse.hrms.model.ClientModel;
import lk.ijse.hrms.util.TimeDate;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private TableView<ClientTM> tableView;

    @FXML
    private TableColumn<?, ?> client_id;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> address;

    @FXML
    private TableColumn<?, ?> contact;

    @FXML
    private TableColumn<?, ?> mail;


    @FXML
    private TableColumn<?, ?> preview;

    @FXML
    private TableColumn<?, ?> delete;


    @FXML
    private JFXTextField txtSearch;


    private boolean isSettingButtonClicked;

    ObservableList<ClientTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TimeDate.localDateAndTime(lblDate, lblTime);
        getAll();
        setCellValueFactory();
        searchFilter();
    }

    private void setCellValueFactory() {
        client_id.setCellValueFactory(new PropertyValueFactory<>("client_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        address.setCellValueFactory(new PropertyValueFactory<>("client_address"));
        contact.setCellValueFactory(new PropertyValueFactory<>("client_contact"));
        mail.setCellValueFactory(new PropertyValueFactory<>("client_email"));
        preview.setCellValueFactory(new PropertyValueFactory<>("client_preview"));
        delete.setCellValueFactory(new PropertyValueFactory<>("client_remove"));

    }

    private void getAll() {


        try {
            List<Client> clientList = ClientModel.getAll();
            for (Client client : clientList) {
                Button btnRemove = new Button();
                Image deleteIcon = new Image(getClass().getResourceAsStream("/assets/remove_logo.png"));
                ImageView deleteView = new ImageView(deleteIcon);
                deleteView.setFitHeight(12);
                deleteView.setFitWidth(12);
                deleteView.setPreserveRatio(true);
                btnRemove.setGraphic(deleteView);
                btnRemove.setCursor(Cursor.HAND);
                btnRemove.setStyle("-fx-background-color:  #ff6666; ");

                setRemoveBtnOnAction(btnRemove); //*set button remove action*/


                Button btnPreview = new Button();
                Image previewIcon = new Image(getClass().getResourceAsStream("/assets/eye.png"));
                ImageView previewView = new ImageView(previewIcon);
                previewView.setFitHeight(12);
                previewView.setFitWidth(12);
                previewView.setPreserveRatio(true);
                btnPreview.setGraphic(previewView);
                btnPreview.setCursor(Cursor.HAND);
                btnPreview.setStyle("-fx-background-color:#87CEEB; ");

                setPreviewBtnOnAction(btnPreview); //*set button preview action*/


                obList.add(new ClientTM(
                        client.getClient_id(),
                        client.getName(),
                        client.getAddress(),
                        client.getContact(),
                        client.getEmail(),
                        btnPreview, btnRemove
                ));
            }
            tableView.setItems(obList);

        } catch (SQLException e) {
        }
    }

    private void setPreviewBtnOnAction(Button btnPreview) {
        btnPreview.setOnAction((e) -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                ObservableList<ClientTM> clients = tableView.getItems();
                ClientTM clientTM = clients.get(selectedIndex);
                String id = clientTM.getClient_id();
                try {
                    Client client = ClientModel.preview(id);
                    ClientPreviewWindowFormController.client = client;
                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/client_preview_window_form.fxml"));
                    Scene scene = new Scene(anchorPane);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Employee Profile");
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
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void setRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {


                    ObservableList<ClientTM> items = tableView.getItems();
                    ClientTM clientTM = items.get(selectedIndex);
                    String client_id = clientTM.getClient_id();


                    boolean isRemove = false;
                    try {
                        isRemove = ClientModel.remove(client_id);

                        if (isRemove) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted !!!").show();
                            obList.clear();
                            getAll();
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

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/add_client_window_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Client Registration");
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
    void btnReportOnAction(ActionEvent event) {

        try {
            JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/Client_Report.jrxml");
            JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
            JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void searchFilter() {
        FilteredList<ClientTM> filteredData = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(ClientTM -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (ClientTM.getClient_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ClientTM.getClient_id().toLowerCase().contains(searchKeyword)) {
                    return true;

                } else if (ClientTM.getClient_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ClientTM.getClient_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ClientTM.getClient_contact().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ClientTM.getClient_contact().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<ClientTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

}
