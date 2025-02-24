package lk.ijse.gdse71.newproject.dao.custom;

import java.sql.SQLException;

public interface ProjectAssignmentDao {
    int getProjectAllEmployeeCount(String selectedProjectId) throws SQLException, ClassNotFoundException;
    }
