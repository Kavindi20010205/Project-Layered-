package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.DTO.BudgetDTO;
import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.BudgetDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BudgetDaoImpl implements BudgetDao {

    public double getBudgetByStatus(String status) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT SUM("+status+") FROM Budget");
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0.0;
    }

    public double getProjectBudgetByStatus(String status,String selectedProjectId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT sum(" + status + ") FROM Budget WHERE Project_ID=?",selectedProjectId);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select Budget_ID from Budget order by Budget_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("BD%03d", newIdIndex);
        }
        return "BD001";
    }
    public ArrayList<BudgetDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Budget");

        ArrayList<BudgetDTO> budgetDTOS = new ArrayList<>();

        while(rst.next()){
            BudgetDTO budgetDTO = new BudgetDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            );
            budgetDTOS.add(budgetDTO);
        }
        return budgetDTOS;
    }

    public boolean save(BudgetDTO budgetDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into Budget values (?,?,?,?,?,?)",
                budgetDTO.getBudgetId(),
                budgetDTO.getProjectId(),
                budgetDTO.getCurrency(),
                budgetDTO.getTotalAmount(),
                budgetDTO.getAllocateAmount(),
                budgetDTO.getSpendAmount()
        );
    }

    public boolean delete(String budgetId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Budget where Budget_ID=?", budgetId);
    }

    public boolean update(BudgetDTO budgetDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update Budget set Project_ID=?, Currency=?, Total_Amount=?, Allocate_Amount=?, Spend_Amount=? where Budget_ID=?",
                budgetDTO.getProjectId(),
                budgetDTO.getCurrency(),
                budgetDTO.getTotalAmount(),
                budgetDTO.getAllocateAmount(),
                budgetDTO.getSpendAmount(),
                budgetDTO.getBudgetId()
        );
    }
}
