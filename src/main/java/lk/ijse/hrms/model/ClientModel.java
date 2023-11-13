package lk.ijse.hrms.model;

import lk.ijse.hrms.db.DBConnection;
import lk.ijse.hrms.dto.Client;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {
    public static boolean save(Client client) throws SQLException {
        String sql = "INSERT INTO client(client_id,name,address,contact,email)VALUES(?,?,?,?,?)";
        String client_id = client.getClient_id();
        String name = client.getName();
        String address = client.getAddress();
        String contact = client.getContact();
        String email = client.getEmail();


        return CrudUtil.execute(sql, client_id, name, address, contact, email);
    }

    public static List<Client> getAll() throws SQLException {
        String sql = "SELECT * FROM client WHERE action='true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<Client> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(new Client(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));

        }
        return data;
    }

    public static boolean remove(String emp_id) throws SQLException {

        String sql = "UPDATE client SET action = ? WHERE client_id = ?";

        String action = "false";
        boolean isDeleted = CrudUtil.execute(sql, action, emp_id);
        return isDeleted;
    }

    public static boolean update(Client client) throws SQLException {
        String sql = "UPDATE  client SET name = ? , address = ? , contact = ? , email = ? WHERE client_id = ?";
        return CrudUtil.execute(sql, client.getName(), client.getAddress(), client.getContact(), client.getEmail(), client.getClient_id());
    }

    public static Client preview(String id) throws SQLException {
        String sql = "SELECT * FROM client WHERE client_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, id);

        if (resultSet.next()) {
            return new Client(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
        }
        return null;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT client_id FROM client WHERE action = 'true'";

        List<String> ids = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }

        return ids;
    }

    public static Client serchById(String client_id) throws SQLException {
        String sql = "SELECT * FROM client WHERE client_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, client_id);

        if (resultSet.next()) {
            return new Client(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        }
        return null;
    }

    public static String searchClientValue() throws SQLException {
        String count = "0";
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(client_id) FROM client WHERE action ='true'";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            count = resultSet.getString(1);
        }
        return count;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT client_id FROM client ORDER BY client_id DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "C000";
    }
}
