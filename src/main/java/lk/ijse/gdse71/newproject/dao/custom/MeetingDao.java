package lk.ijse.gdse71.newproject.dao.custom;

import java.sql.SQLException;

public interface MeetingDao {
     int getProjectAllMeetingCount(String selectedProjectId) throws SQLException, ClassNotFoundException;
    }
