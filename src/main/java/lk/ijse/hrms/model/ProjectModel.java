package lk.ijse.hrms.model;

import lk.ijse.hrms.db.DBConnection;
import lk.ijse.hrms.dto.Project;
import lk.ijse.hrms.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectModel {
    public static boolean add(Project project, String emp1, String emp2, String emp3, String emp4, String emp5) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isAdded = ProjectModel.addProject(project);
            if (isAdded) {
                boolean empAdded = ProjectEmployeeModel.add(project.getProj_id(), emp1, emp2, emp3, emp4, emp5);
                if (empAdded) {
                    con.setAutoCommit(true);
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
            return false;
        } finally {

            con.setAutoCommit(true);
        }

    }

    private static boolean addProject(Project project) throws SQLException {
        String sql = "INSERT INTO project(proj_id,proj_name,duration,proj_manager,cost,proj_phases) VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(sql, project.getProj_id(), project.getProj_name(), project.getDuration(), project.getProj_manager(),
                project.getCost(), project.getProj_phases());
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT proj_id  FROM project";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;

    }

    public static Project searchById(String proj_id) throws SQLException {
        String sql = "SELECT * FROM project WHERE proj_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql,proj_id);

        if (resultSet.next()) {
            return new Project(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(1));
        }
        return null;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT proj_id FROM project ORDER BY proj_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return "P000";

    }
}
