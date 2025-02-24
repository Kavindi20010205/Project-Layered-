package lk.ijse.gdse71.newproject.dao.custom;

import lk.ijse.gdse71.newproject.DTO.EmployeeDTO;

import java.sql.SQLException;

public interface EmployeeDao {
    EmployeeDTO findById(String employeeID) throws SQLException, ClassNotFoundException;
    }
