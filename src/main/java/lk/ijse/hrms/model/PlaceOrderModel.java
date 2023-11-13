package lk.ijse.hrms.model;

import lk.ijse.hrms.db.DBConnection;
import lk.ijse.hrms.dto.Cart;
import lk.ijse.hrms.model.OrderModel;
import lk.ijse.hrms.model.OrderProjectModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderModel {
    public static boolean placeOrder(String oId, String cusId, List<Cart> cartDTOList, String netTotal) throws SQLException {

        Connection con = null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean isAdded = OrderModel.add(oId, LocalDate.now(), netTotal, cusId);
            if (isAdded) {
                boolean isAdd = OrderProjectModel.add(oId, cartDTOList);
                if (isAdd) {
                    con.setAutoCommit(true);
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
            return false;
        } finally {
            con.setAutoCommit(true);
        }

    }
}
