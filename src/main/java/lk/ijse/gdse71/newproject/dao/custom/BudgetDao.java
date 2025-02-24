package lk.ijse.gdse71.newproject.dao.custom;

import lk.ijse.gdse71.newproject.DTO.BudgetDTO;
import lk.ijse.gdse71.newproject.dao.CrudDao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BudgetDao extends CrudDao<BudgetDTO> {

    double getBudgetByStatus(String status) throws SQLException, ClassNotFoundException;
    double getProjectBudgetByStatus(String status,String selectedProjectId) throws SQLException, ClassNotFoundException;
//    String getNextBudgetId() throws SQLException ;
//    ArrayList<BudgetDTO> getAllBudgets() throws SQLException ;
//    boolean saveBudget(BudgetDTO budgetDTO) throws SQLException ;
//    boolean deleteBudget(String budgetId) throws SQLException;
//    boolean updateBudget(BudgetDTO budgetDTO) throws SQLException;
}
