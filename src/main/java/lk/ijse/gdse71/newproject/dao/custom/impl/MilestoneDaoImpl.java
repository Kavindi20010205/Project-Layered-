package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.MilestoneDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MilestoneDaoImpl implements MilestoneDao {
    public double getProjectMilestoneCount(String selectedProjectId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT (SUM(CASE WHEN Status = 'Complete' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS completionPercentage " +
                        "FROM Milestone WHERE Project_ID = ?",
                selectedProjectId
        );

        if (rst.next()) {
            return rst.getDouble("completionPercentage");
        }
        return 0.0;
    }
}
