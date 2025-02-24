package lk.ijse.gdse71.newproject.dao.custom;

import lk.ijse.gdse71.newproject.DTO.DocumentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DocumentDao {

     int getProjectDocumentCount(String selectedProjectId) throws SQLException, ClassNotFoundException;
     String getNextDocumentId() throws SQLException, ClassNotFoundException;
     ArrayList<DocumentDTO> getAllDocumentDetails() throws SQLException, ClassNotFoundException;
     ArrayList<String> getStatusNames() throws SQLException, ClassNotFoundException;
     ArrayList<DocumentDTO> getAllDocumentsByStatus(String selectedStatus) throws SQLException, ClassNotFoundException;
     boolean cancelDocument(String updateDocumentID) throws SQLException, ClassNotFoundException;
     boolean holdDocument(String updateDocumentID) throws SQLException, ClassNotFoundException;
     DocumentDTO findById(String updateDocumentID) throws SQLException, ClassNotFoundException;
     boolean saveDocument(DocumentDTO documentDTO) throws SQLException, ClassNotFoundException;
}
