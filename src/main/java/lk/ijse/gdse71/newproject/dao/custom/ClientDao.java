package lk.ijse.gdse71.newproject.dao.custom;

import lk.ijse.gdse71.newproject.DTO.ClientDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientDao {

    ArrayList<String> getAllClientIds() throws SQLException, ClassNotFoundException;
    ClientDTO findById(String selectedClientId) throws SQLException, ClassNotFoundException;

}
