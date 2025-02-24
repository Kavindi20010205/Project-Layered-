package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.DTO.ClientDTO;
import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.ClientDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDaoImpl implements ClientDao {
    public ArrayList<String> getAllClientIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select Client_ID from Client");

        ArrayList<String> serviceIds = new ArrayList<>();

        while (rst.next()) {
            serviceIds.add(rst.getString(1));
        }

        return serviceIds;
    }
    public ClientDTO findById(String selectedClientId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Client where Client_ID=?", selectedClientId);

        if (rst.next()) {
            return new ClientDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
