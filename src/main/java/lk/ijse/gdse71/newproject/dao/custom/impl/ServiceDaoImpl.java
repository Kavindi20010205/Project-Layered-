package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.DTO.ServiceDTO;
import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.ServiceDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDaoImpl implements ServiceDao {
    public ArrayList<String> getAllServiceIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select Service_ID from Service");

        ArrayList<String> serviceIds = new ArrayList<>();

        while (rst.next()) {
            serviceIds.add(rst.getString(1));
        }

        return serviceIds;
    }

    public ServiceDTO findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Service where Service_ID=?", selectedServiceId);

        if (rst.next()) {
            return new ServiceDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;
    }
}
