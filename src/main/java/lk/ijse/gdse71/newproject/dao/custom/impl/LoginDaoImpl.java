package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.DTO.LoginDTO;
import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.LoginDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDaoImpl implements LoginDao {
    public LoginDTO findById(String userNameGiven) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Login where User_Name=?", userNameGiven);

        if (rst.next()) {
            return new LoginDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
        }
        return null;
    }
}
