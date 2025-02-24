package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.RiskDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RiskDaoImpl implements RiskDao {
    public int getProjectRiskCount(String selectedProjectId, String state) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(Risk_ID) AS stateCount FROM Risk WHERE Project_ID = ? AND Impact = ?",selectedProjectId, state);
        if (rst.next()) {
            return rst.getInt("stateCount");
        }
        return 0;
    }
}
