package lk.ijse.gdse71.newproject.dao.custom;

import lk.ijse.gdse71.newproject.DTO.ServiceDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ServiceDao {
    ArrayList<String> getAllServiceIds() throws SQLException, ClassNotFoundException;
    ServiceDTO findById(String selectedServiceId) throws SQLException, ClassNotFoundException;
}
