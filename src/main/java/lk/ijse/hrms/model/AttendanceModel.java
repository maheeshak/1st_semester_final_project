package lk.ijse.hrms.model;

import lk.ijse.hrms.dto.Attendance;
import lk.ijse.hrms.dto.AttendanceDetails;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {
    public static boolean checkValid(String emp_id, String date) throws SQLException {
        String sql = "SELECT * FROM attendance WHERE emp_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, emp_id);

        while (resultSet.next()) {
            if (date.equals(resultSet.getString(2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean add(String emp_id, String date, String time, String attd_type) throws SQLException {
        String sql = "INSERT INTO attendance(emp_id,atd_date,atd_time,atd_type) VALUES(?,?,?,?)";

        return CrudUtil.execute(sql, emp_id, date, time, attd_type);
    }

    public static List<Attendance> getAll() throws SQLException {
        String sql = "SELECT * FROM attendance";
        ResultSet resultset = CrudUtil.execute(sql);
        List<Attendance> attendanceList = new ArrayList<>();


        while (resultset.next()) {
            Employee employee = EmployeeModel.searchById(resultset.getString(1));
            attendanceList.add(new Attendance(resultset.getString(1), employee.getName(), employee.getDesignation(), resultset.getString(2), resultset.getString(4)));
        }
        return attendanceList;
    }

    public static AttendanceDetails preview(String id, String date) throws SQLException {
        String sql = "SELECT * FROM attendance WHERE emp_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, id);

        while (resultSet.next()) {

            Employee employee = EmployeeModel.preview(id);
            if(resultSet.getString(2).equals(date))
            return new AttendanceDetails(resultSet.getString(1), employee.getName(), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
        }
        return null;
    }

    public static String getAttendanceValue(String on_time, String half_day, String late) throws SQLException {
        String sql = "SELECT COUNT(atd_type) FROM attendance WHERE (atd_type = ? OR atd_type = ? OR atd_type = ?)  AND atd_date = ?";
        ResultSet resultSet = CrudUtil.execute(sql,on_time,half_day,late,LocalDate.now());
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "00";
    }

    public static String setLateValue(String late) throws SQLException {
        String sql = "SELECT COUNT(atd_type) FROM attendance WHERE atd_type = ? AND atd_date = ?";
        ResultSet resultSet = CrudUtil.execute(sql,late, LocalDate.now());
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "00";
    }

    public static String setAbsValue(String no_pay) throws SQLException {
        String sql = "SELECT COUNT(atd_type) FROM attendance WHERE  atd_type = ? AND atd_date= ?;";
        ResultSet resultSet = CrudUtil.execute(sql,no_pay,LocalDate.now());
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "00";
    }
}
