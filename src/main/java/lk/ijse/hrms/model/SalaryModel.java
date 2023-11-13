package lk.ijse.hrms.model;

import lk.ijse.hrms.dto.tm.SalaryTM;
import lk.ijse.hrms.util.CrudUtil;
import org.apache.commons.collections.functors.WhileClosure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public static String searchById(String salId) throws SQLException {
        String sql = "SELECT sal_basic FROM salary WHERE sal_id  = ? ";
        ResultSet resultSet = CrudUtil.execute(sql, salId);

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0000.00";
    }

    public static List<SalaryTM> getAll() throws SQLException {
        String sql = "SELECT * FROM emp_salary";

        ResultSet resultSet = CrudUtil.execute(sql);
        List<SalaryTM> salaryTM = new ArrayList<>();
        while (resultSet.next()) {
            salaryTM.add(new SalaryTM(resultSet.getString(1), resultSet.getString(7), resultSet.getDouble(3),
                    resultSet.getDouble(4), resultSet.getDouble(5), resultSet.getDouble(6)));
        }
        return salaryTM;
    }

    public static boolean check(String emp_id, String month) throws SQLException {
        String sql = "SELECT emp_id FROM emp_salary WHERE month = ?";

        ResultSet resultSet = CrudUtil.execute(sql,month);
            while (resultSet.next()) {
                if (emp_id.equals(resultSet.getString(1))){
                    return false;

            }
        }
            return true;
    }


    public static boolean add(String emp_id, String sal_id, Double basic, Double totalEarnings, Double totalDeductions, Double netSalary, String month) throws SQLException {
        String sql = "INSERT INTO emp_salary (emp_id,salary_id,basic,earnings,deductions,net_salary,month) VALUES(?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,emp_id,sal_id,basic,totalEarnings,totalDeductions,netSalary,month);
    }
}
