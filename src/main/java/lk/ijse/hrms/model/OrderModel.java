package lk.ijse.hrms.model;

import lk.ijse.hrms.db.DBConnection;
import lk.ijse.hrms.dto.Client;
import lk.ijse.hrms.dto.Order;
import lk.ijse.hrms.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static boolean add(String oId, LocalDate now, String netTotal, String cusId) throws SQLException {
        String sql ="INSERT INTO order_details(order_id,order_date,order_price,cust_id) VALUES(?,?,?,?)";
        return CrudUtil.execute(sql,oId,now,netTotal,cusId);
    }

    public static String generateNextOrderId() throws SQLException {
            String sql = "SELECT order_id FROM order_details ORDER BY order_id DESC LIMIT 1";
            ResultSet resultSet = CrudUtil.execute(sql);
            if (resultSet.next()) {
                return splitOrderId(resultSet.getString(1));
            }
            return splitOrderId(null);
        }

        public static String splitOrderId(String currentOrderId) {
            if (currentOrderId != null) {
                String[] strings = currentOrderId.split("O00-");
                int id = Integer.parseInt(strings[1]);
                id++;

                return "O00-" + id;
            }
            return "O00-1";
        }

    public static List<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM order_details";

        ResultSet resultSet = CrudUtil.execute(sql);

        List<Order> order_list= new ArrayList<>();

        while (resultSet.next()){
            String client_id = resultSet.getString(5);
            Client client = ClientModel.serchById(client_id);
            String client_name = client.getName();

            order_list.add(new Order(resultSet.getString(1),resultSet.getString(5),
                    client_name,resultSet.getDouble(3),resultSet.getString(2)));

        }
        return order_list;
    }

    public static String searchOrderValue() throws SQLException {
        String count = "0";
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(order_id) FROM order_details ";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            count = resultSet.getString(1);
        }
        return count;
    }

    public static Order searchById(String id) throws SQLException {
        String sql = "SELECT * FROM order_details WHERE order_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql,id);


        while (resultSet.next()){
            String client_id = resultSet.getString(5);
            Client client = ClientModel.serchById(client_id);
            String client_name = client.getName();

            return new Order(resultSet.getString(1),resultSet.getString(5),
                    client_name,resultSet.getDouble(3),resultSet.getString(2));

        }
        return null;
    }
}
