package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.ChangeRequestDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeRequestDaoImpl implements ChangeRequestDao {
    public double getProjectChangeRequestCount(String selectedProjectId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT (SUM(CASE WHEN Status = 'Approved' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS completionPercentage " +
                        "FROM Change_Request WHERE Project_ID = ?",
                selectedProjectId
        );

        if (rst.next()) {
            return rst.getDouble("completionPercentage");
        }
        return 0.0;
    }
}
