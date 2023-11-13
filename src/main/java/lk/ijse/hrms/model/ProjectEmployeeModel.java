package lk.ijse.hrms.model;

import lk.ijse.hrms.util.CrudUtil;

import java.sql.SQLException;

public class ProjectEmployeeModel {
    public static boolean add(String proj_id, String emp1, String emp2, String emp3, String emp4, String emp5) throws SQLException {
       boolean isAdded = false;
        if (!emp1.isEmpty()){
            String sql = "INSERT INTO proj_emp (proj_id,emp_id) VALUES(?,?)";
            CrudUtil.execute(sql,proj_id,emp1);
            isAdded = true;
        }if(!emp2.isEmpty()){
            String sql = "INSERT INTO proj_emp (proj_id,emp_id) VALUES(?,?)";
            CrudUtil.execute(sql,proj_id,emp2);
            isAdded = true;

        } if(!emp3.isEmpty()){
            String sql = "INSERT INTO proj_emp (proj_id,emp_id) VALUES(?,?)";
            CrudUtil.execute(sql,proj_id,emp3);
            isAdded = true;

        } if(!emp4.isEmpty()){
            String sql = "INSERT INTO proj_emp (proj_id,emp_id) VALUES(?,?)";
            CrudUtil.execute(sql,proj_id,emp4);
            isAdded = true;

        } if(!emp5.isEmpty()){
            String sql = "INSERT INTO proj_emp (proj_id,emp_id) VALUES(?,?)";
            CrudUtil.execute(sql,proj_id,emp5);
            isAdded = true;

        }
        return isAdded;
    }
}
