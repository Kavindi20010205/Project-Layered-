package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.ProjectAssignmentDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectAssignmentDaoImpl implements ProjectAssignmentDao {
    public int getProjectAllEmployeeCount(String selectedProjectId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(Employee_ID) AS employeeCount FROM Project_Assignment WHERE Project_ID=?", selectedProjectId);
        if (rst.next()) {
            return rst.getInt("employeeCount");
        }
        return 0;

    }
}
