package lk.ijse.hrms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hrms.dto.Order;
import lk.ijse.hrms.dto.tm.OrderPreviewTM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderPreviewWindowFormController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblCustomerName;

    @FXML
    private TableView<OrderPreviewTM> tblOrderCart;

    @FXML
    private TableColumn<?, ?> colProjCode;

    @FXML
    private TableColumn<?, ?> colProjName;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private Label lblClientId;

    public static Order order;
    public static List<OrderPreviewTM> orderPreviewTM;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValues();
        setCellValueFactory();
        setTableValue();
    }

    private void setTableValue() {
        ObservableList<OrderPreviewTM> obList = FXCollections.observableArrayList();

        for (int i = 0; i < orderPreviewTM.size(); i++) {
            obList.add(orderPreviewTM.get(i));

        }
        tblOrderCart.setItems(obList);

    }

    private void setCellValueFactory() {
        colProjCode.setCellValueFactory(new PropertyValueFactory<>("proj_id"));
        colProjName.setCellValueFactory(new PropertyValueFactory<>("proj_name"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private void setValues() {
        lblClientId.setText(order.getClient_id());
        lblCustomerName.setText(order.getClient_name());
        lblOrderDate.setText(order.getDate());
        lblOrderId.setText(order.getOrder_id());
    }
}
