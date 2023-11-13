package lk.ijse.hrms.model;

import lk.ijse.hrms.dto.Cart;
import lk.ijse.hrms.dto.Project;
import lk.ijse.hrms.dto.tm.OrderPreviewTM;
import lk.ijse.hrms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProjectModel {
    public static boolean add(String oId, List<Cart> cartDTOList) throws SQLException {
        for (Cart dto : cartDTOList) {
            if (!save(oId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, Cart dto) throws SQLException {

        String sql = "INSERT INTO order_project(order_id, proj_id, cost) VALUES (?, ?, ?)";

        return CrudUtil.execute(sql, oId, dto.getProj_id(),dto.getCost());

    }

    public static List<OrderPreviewTM> searchById(String id) throws SQLException {
        String sql = "SELECT * FROM order_project WHERE order_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql,id);

        List<OrderPreviewTM> orderPreviewTM = new ArrayList<>();

        while (resultSet.next()){
            String proj_id = resultSet.getString(2);
            Project project = ProjectModel.searchById(proj_id);
            String proj_name = project.getProj_name();
            orderPreviewTM.add(new OrderPreviewTM(resultSet.getString(2),proj_name,resultSet.getString(3)));

        }
        return orderPreviewTM;
    }
}
