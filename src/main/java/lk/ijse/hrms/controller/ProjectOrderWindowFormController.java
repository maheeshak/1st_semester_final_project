package lk.ijse.hrms.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import lk.ijse.hrms.dto.Cart;
import lk.ijse.hrms.dto.Client;
import lk.ijse.hrms.dto.tm.PlaceOrderTM;
import lk.ijse.hrms.model.*;
import lk.ijse.hrms.dto.Project;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectOrderWindowFormController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private JFXComboBox<String> cmbProjectCode;

    @FXML
    private Label lblProjectName;

    @FXML
    private Label lblPrice;

    @FXML
    private TableView<PlaceOrderTM> tblOrderCart;

    @FXML
    private TableColumn<?, ?> colProjCode;

    @FXML
    private TableColumn<?, ?> colProjName;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private Label lblNetTotal;

    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setClientIds();
        setItemIds();
        setOrderId();
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colProjCode.setCellValueFactory(new PropertyValueFactory<>("proj_id"));
        colProjName.setCellValueFactory(new PropertyValueFactory<>("proj_name"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }
    private void setOrderId() {

        try {
            String order_id= OrderModel.generateNextOrderId();
            lblOrderId.setText(order_id);
        } catch (SQLException e) {}

    }
    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblOrderCart.refresh();
                calculateNetTotal();
            }

        });
    }
        private void setItemIds() {
        try {

            List<String> proj_ids = ProjectModel.getIds();

            cmbProjectCode.getItems().addAll(proj_ids);
        } catch (SQLException e) {
        }
    }


    private void setClientIds() {
        try {

            List<String> ids = ClientModel.getIds();

            cmbCustomerId.getItems().addAll(ids);
        } catch (SQLException e) {
        }
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String proj_id = cmbProjectCode.getValue();
        String proj_name = lblProjectName.getText();
        double price = Double.parseDouble(lblPrice.getText());

        Button btnRemove = new Button();
        Image deleteIcon = new Image(getClass().getResourceAsStream("/assets/remove_logo.png"));
        ImageView deleteView = new ImageView(deleteIcon);
        deleteView.setFitHeight(12);
        deleteView.setFitWidth(12);
        deleteView.setPreserveRatio(true);
        btnRemove.setGraphic(deleteView);
        btnRemove.setCursor(Cursor.HAND);
        btnRemove.setStyle("-fx-background-color:  #ff6666; ");

        setRemoveBtnOnAction(btnRemove);  /*set action to the btnRemove */


        PlaceOrderTM tm = new PlaceOrderTM(proj_id, proj_name, price, btnRemove);

        obList.add(tm);
        tblOrderCart.setItems(obList);
        calculateNetTotal();


    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            double total = (double) colCost.getCellData(i);
            netTotal += total;
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/add_client_window_form.fxml"));

            Scene scene = new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Client Registration");
            stage.setResizable(false);
            Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
            stage.getIcons().add(icon);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String oId = lblOrderId.getText();
        String cusId = cmbCustomerId.getValue();
        String netTotal = lblNetTotal.getText();


        List<Cart> cartDTOList = new ArrayList<>();

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            PlaceOrderTM tm = obList.get(i);

            Cart cartDTO = new Cart(tm.getProj_id(), tm.getPrice());
            cartDTOList.add(cartDTO);
        }

        try {
            boolean isPlaced = PlaceOrderModel.placeOrder(oId, cusId, cartDTOList, netTotal);
            if (isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }


    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String client_id = cmbCustomerId.getSelectionModel().getSelectedItem();

        try {
            Client client = ClientModel.serchById(client_id);
            lblCustomerName.setText(client.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void cmbProjectOnAction(ActionEvent event) {
        String proj_id = cmbProjectCode.getSelectionModel().getSelectedItem();

        try {

            Project project = ProjectModel.searchById(proj_id);

            lblProjectName.setText(project.getProj_name());
            lblPrice.setText(String.valueOf(project.getCost()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
