package lk.ijse.hrms.model;

import javafx.scene.image.Image;
import lk.ijse.hrms.db.DBConnection;
import lk.ijse.hrms.dto.Employee;

import lk.ijse.hrms.util.CrudUtil;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static String searchEmployeeValue() throws SQLException {
        String count = "0";
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(emp_id) FROM employee WHERE action ='true'";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            count = resultSet.getString(1);
        }
        return count;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee WHERE action='true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<Employee> data = new ArrayList<>();
        while (resultSet.next()) {

            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getString(13),
                    resultSet.getString(14),
                    resultSet.getString(15)
            ));
        }
        return data;
    }

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee(dep_id,emp_id,emp_name,emp_nic,address,age,dob,gender,mobile,home,email,designation,experience_yrs,academic_qualification,professional_qualification,sal_id) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, employee.getDep_id(), employee.getEmp_id(), employee.getName(),
                employee.getNic(), employee.getAddress(), employee.getAge(), employee.getDob(), employee.getGender(), employee.getMobile(),
                employee.getHome(), employee.getEmail(), employee.getDesignation(), employee.getExperience(), employee.getAcademic(), employee.getProfessional(), "S001");
        return isSaved;
    }

    public static boolean remove(String emp_id) throws SQLException {
        String sql = "UPDATE employee SET action = ? WHERE emp_id = ?";

        String action = "false";
        boolean isDeleted = CrudUtil.execute(sql, action, emp_id);
        return isDeleted;
    }

    public static Employee preview(String emp_id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE emp_id = ? AND action='true'";
        ResultSet resultSet = CrudUtil.execute(sql, emp_id);

        if (resultSet.next()) {
            return new Employee(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getString(8), resultSet.getString(9),
                    resultSet.getString(10), resultSet.getString(11), resultSet.getString(12),
                    resultSet.getString(13), resultSet.getString(14), resultSet.getString(15));

        }
        return null;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDate employee SET dep_id = ?,emp_name =?,emp_nic = ?,address = ?,age = ?,dob = ?,gender = ?,mobile = ?,home = ?,email = ?,designation = ?,experience_yrs = ?,academic_qualification = ? ,professional_qualification = ?,sal_id = ? " +
                "WHERE emp_id =?";
        return CrudUtil.execute(sql, employee.getDep_id(), employee.getName(), employee.getNic(), employee.getAddress(), employee.getAge(), employee.getDob()
                , employee.getGender(), employee.getMobile(), employee.getHome(), employee.getEmail(), employee.getDesignation(), employee.getExperience(), employee.getAcademic()
                , employee.getProfessional(), "S001", employee.getEmp_id());
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT emp_id FROM employee WHERE action = 'true'";
        List<String> ids = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static Employee searchById(String empId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE emp_id = ? ";
        ResultSet resultSet = CrudUtil.execute(sql, empId);
        if (resultSet.next()) {
            return new Employee(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getString(13),
                    resultSet.getString(14),
                    resultSet.getString(15));
        }
        return null;
    }

    public static boolean addImage(FileInputStream fileInputStream, String emp_id, File file) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET image = ? WHERE emp_id = ?";


        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setBinaryStream(1, (InputStream) fileInputStream, (int) file.length());
        pstm.setString(2, emp_id);


        return 0 < pstm.executeUpdate();
    }

    public static Image getImage(String emp_id) throws FileNotFoundException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            String sql = "SELECT  * FROM employee WHERE emp_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, emp_id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                InputStream inputStream = resultSet.getBinaryStream(18);

                OutputStream outputStream = new FileOutputStream(new File("photo.jpg"));
                byte[] connect = new byte[1024];

                int size = 0;
                while ((size = inputStream.read(connect)) != -1) {
                    outputStream.write(connect, 0, size);

                }
                outputStream.close();
                inputStream.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        Image image = new Image("file:photo.jpg", 100, 100, true, true);

        return image;
    }

    public static String getCurrentID() throws SQLException {
        String sql = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "E000";
    }

    public static Integer[] getEmployeeValueMonths() throws SQLException {
        Integer[] data = new Integer[12];
        int jan = 0;
        int feb = 0;
        int mar = 0;
        int apr = 0;
        int may = 0;
        int jun = 0;
        int jul = 0;
        int aug = 0;
        int sep = 0;
        int oct = 0;
        int nov = 0;
        int dec = 0;

        String sql = "SELECT MONTH(atd_date), COUNT(emp_id) FROM attendance WHERE atd_type != 'No pay' GROUP BY MONTH(atd_date)";

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            switch (resultSet.getString(1)) {

                case "1":
                    jan = Integer.parseInt(resultSet.getString(2));
                    break;
                case "2":
                    feb = Integer.parseInt(resultSet.getString(2));
                    break;
                case "3":
                    mar = Integer.parseInt(resultSet.getString(2));
                    break;
                case "4":
                    apr = Integer.parseInt(resultSet.getString(2));
                    break;
                case "5":
                    may = Integer.parseInt(resultSet.getString(2));
                    break;
                case "6":
                    jun = Integer.parseInt(resultSet.getString(2));
                    break;
                case "7":
                    jul = Integer.parseInt(resultSet.getString(2));
                    break;
                case "8":
                    aug = Integer.parseInt(resultSet.getString(2));
                    break;
                case "9":
                    sep = Integer.parseInt(resultSet.getString(2));
                    break;
                case "10":
                    oct = Integer.parseInt(resultSet.getString(2));
                    break;
                case "11":
                    nov = Integer.parseInt(resultSet.getString(2));
                    break;
                case "12":
                    dec = Integer.parseInt(resultSet.getString(2));
                    break;

            }

            data[0] = jan;
            data[1] = feb;
            data[2] = mar;
            data[3] = apr;
            data[4] = may;
            data[5] = jun;
            data[6] = jul;
            data[7] = aug;
            data[8] = sep;
            data[9] = oct;
            data[10] = nov;
            data[11] = dec;
        }
        return data;


    }
}
