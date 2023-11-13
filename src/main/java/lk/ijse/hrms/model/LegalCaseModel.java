package lk.ijse.hrms.model;

import lk.ijse.hrms.dto.EmployeeLegalCases;
import lk.ijse.hrms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LegalCaseModel {
    public static boolean add(String empID,String legCase, String legId, String desc) throws SQLException {
        String sql ="INSERT INTO legal_case (leg_id,emp_id,leg_desc,leg_case,date) VALUES(?,?,?,?,?)";

        return CrudUtil.execute(sql,legId,empID,desc,legCase, LocalDate.now());
    }

    public static List<EmployeeLegalCases> getAll() throws SQLException {
        String sql ="SELECT * FROM legal_case WHERE action = 'true'";
        List<EmployeeLegalCases> leg_cases= new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            leg_cases.add(new EmployeeLegalCases(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)));
        }
        return leg_cases;
    }

    public static boolean remove(String emp_id) throws SQLException {
        String sql = "UPDATE legal_case SET action ='false' WHERE  emp_id = ?";

        return CrudUtil.execute(sql,emp_id);
    }

    public static EmployeeLegalCases find(String emp_id, String leg_id) throws SQLException {
        String sql = "SELECT * FROM legal_case WHERE leg_case = ? && emp_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql,leg_id,emp_id);

        if (resultSet.next()){
            return new EmployeeLegalCases(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5));
        }
        return null;
    }

    public static boolean update(String leg_id, String leg_case, String leg_desc) throws SQLException {
        String sql ="UPDATE legal_case SET leg_case = ? , leg_desc = ? WHERE leg_id = ?";
        return CrudUtil.execute(sql,leg_case,leg_desc,leg_id);
    }
}
