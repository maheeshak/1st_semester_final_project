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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.hrms.dto.Order;
import lk.ijse.hrms.dto.tm.OrderPreviewTM;
import lk.ijse.hrms.dto.tm.ProjectTM;
import lk.ijse.hrms.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static lk.ijse.hrms.util.TimeDate.localDateAndTime;

public class ProjectWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private TableView<ProjectTM> tblProjects;

    @FXML
    private TableColumn<?, ?> colOrderId;


    @FXML
    private TableColumn<?, ?> colClientId;

    @FXML
    private TableColumn<?, ?> colClientName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colPreview;
    @FXML
    private TableColumn<?, ?> colDate;

    private boolean isSettingButtonClicked;

    @FXML
    private JFXTextField txtSearch;

    ObservableList<ProjectTM> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localDateAndTime(lblDate, lblTime);
        setCellValueFactory();
        getAll();
        searchFilter();

    }

    private void getAll() {
        try {
            List<Order> orderList = OrderModel.getAll();


            for (Order order : orderList) {


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
                obList.add(new ProjectTM(order.getOrder_id(), order.getClient_id(),
                        order.getClient_name(), order.getPrice(), order.getDate(), btnPreview));

            }
            tblProjects.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPreviewBtnOnAction(Button btnPreview) {
        btnPreview.setOnAction((e) -> {
            int selectedIndex = tblProjects.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                ObservableList<ProjectTM> projects = tblProjects.getItems();
                ProjectTM projectTM = projects.get(selectedIndex);
                String id = projectTM.getOrder_id();
                try {
                    Order order = OrderModel.searchById(id);
                    OrderPreviewWindowFormController.order = order;

                    List<OrderPreviewTM> orderPreviewTM = OrderProjectModel.searchById(id);

                    OrderPreviewWindowFormController.orderPreviewTM = orderPreviewTM;

                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/order_preview_window_form.fxml"));
                    Scene scene = new Scene(anchorPane);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Employee Profile");
                    stage.setResizable(false);
                    Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
                    stage.getIcons().add(icon);

                    stage.show();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("client_id"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/add_project_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Project Profile");
        stage.setResizable(false);
        Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
        stage.getIcons().add(icon);


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
    void btnOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/project_order_window_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Order Projects");
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

    private void searchFilter() {
        FilteredList<ProjectTM> filteredData = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(ProjectTM -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (ProjectTM.getOrder_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ProjectTM.getOrder_id().toLowerCase().contains(searchKeyword)) {
                    return true;

                } else if (ProjectTM.getClient_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ProjectTM.getClient_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ProjectTM.getDate().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ProjectTM.getDate().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<ProjectTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblProjects.comparatorProperty());
        tblProjects.setItems(sortedData);
    }

    @FXML
    void txtSearchOnKeyPressed(KeyEvent event) {
        searchFilter();
    }

}
