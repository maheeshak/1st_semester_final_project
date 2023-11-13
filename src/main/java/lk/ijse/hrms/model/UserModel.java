package lk.ijse.hrms.model;

import lk.ijse.hrms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean save(String userNameText, String passwordText, String selectedItem) throws SQLException {
        String sql = "INSERT INTO user(user_name, password, user_type) VALUES(?, ?, ?)";
        return CrudUtil.execute(sql, userNameText,passwordText,selectedItem);
    }

    public static boolean check(String userNameText, String passwordText) throws SQLException {
        boolean isValid=false;
        String sql = "SELECT * FROM user WHERE user_name = ?";
        ResultSet resultSet = CrudUtil.execute(sql, userNameText);

        if(resultSet.next()){
            String password = resultSet.getString(2);
            if (password.equals(passwordText)){
                isValid=true;
            }
        }
        return isValid;
    }
}
