package lk.ijse.gdse71.newproject.dao.custom;

import lk.ijse.gdse71.newproject.DTO.LoginDTO;

import java.sql.SQLException;

public interface LoginDao {
    LoginDTO findById(String userNameGiven) throws SQLException, ClassNotFoundException;
}
