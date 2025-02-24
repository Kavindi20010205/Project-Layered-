package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.DTO.EmployeeDTO;
import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.EmployeeDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {
    public EmployeeDTO findById(String employeeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Employee where Employee_ID=?", employeeID);
        if (rst.next()) {
            return new EmployeeDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getInt(8)
            );
        }
        return null;
    }
}
