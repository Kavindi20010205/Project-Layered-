package lk.ijse.gdse71.newproject.dao.custom;

import java.sql.SQLException;

public interface MilestoneDao {
    double getProjectMilestoneCount(String selectedProjectId) throws SQLException, ClassNotFoundException;
    }
