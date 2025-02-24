package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.MeetingDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingDaoImpl implements MeetingDao {
    public int getProjectAllMeetingCount(String selectedProjectId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(Meeting_ID) AS meetingCount FROM Meeting WHERE Project_ID=?", selectedProjectId);
        if (rst.next()) {
            return rst.getInt("meetingCount");
        }
        return 0;

    }
}
