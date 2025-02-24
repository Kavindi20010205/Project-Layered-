package lk.ijse.gdse71.newproject.dao.custom;

import java.sql.SQLException;

public interface RiskDao {
    int getProjectRiskCount(String selectedProjectId, String state) throws SQLException, ClassNotFoundException;
}
