package lk.ijse.gdse71.newproject.dao;

import lk.ijse.gdse71.newproject.DTO.BudgetDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDao<T> extends SuperDao{
    ArrayList<BudgetDTO> getAll() throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException, ClassNotFoundException;
    boolean save(BudgetDTO budgetDTO) throws SQLException, ClassNotFoundException;
    boolean delete(String budgetId) throws SQLException, ClassNotFoundException;
    boolean update(BudgetDTO budgetDTO) throws SQLException, ClassNotFoundException;
}
