package lk.ijse.gdse71.newproject.dao.custom;

import java.sql.SQLException;

public interface ChangeRequestDao {
    public double getProjectChangeRequestCount(String selectedProjectId) throws SQLException, ClassNotFoundException;
}
