package lk.ijse.hrms.model;

import lk.ijse.hrms.dto.EmployeeDisAction;
import lk.ijse.hrms.util.CrudUtil;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaryActionModel {
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT disc_id FROM disciplinary_action";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static String serchById(String disId) throws SQLException {
        String sql = "SELECT * FROM disciplinary_action WHERE disc_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, disId);

        if (resultSet.next()) {
            return resultSet.getString(2);
        }
        return "-";
    }

    public static boolean add(String empId, String disId, LocalDate now) throws SQLException {
        String sql = "INSERT INTO emp_disciplinary (emp_id,disc_id,disc_date) VALUES (?,?,?)";

        return CrudUtil.execute(sql, empId, disId, now);
    }

    public static List<EmployeeDisAction> getAll() throws SQLException {
        String sql = "SELECT * FROM emp_disciplinary";

        ResultSet resultSet = CrudUtil.execute(sql);

        List<EmployeeDisAction> disc = new ArrayList<>();
        while (resultSet.next()) {

            disc.add(new EmployeeDisAction(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3)));

        }
        return disc;
    }

    public static boolean remove(String emp_id, String dis_action) throws SQLException {
        String sql = "DELETE FROM emp_disciplinary WHERE emp_id = ? && disc_id = ?";

        return CrudUtil.execute(sql, emp_id, dis_action);
    }

    public static EmployeeDisAction find(String emp_id, String disc_id) throws SQLException {

        String sql = "SELECT * FROM emp_disciplinary WHERE emp_id = ? && disc_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql,emp_id,disc_id);
        if (resultSet.next()) {
            return new EmployeeDisAction(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3));

        }
        return null;
    }
}
