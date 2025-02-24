package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.DTO.DocumentDTO;
import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.DocumentDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocumentDaoImpl implements DocumentDao {
    public int getProjectDocumentCount(String selectedProjectId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(Document_ID) AS documentCount FROM Document WHERE Project_ID=?", selectedProjectId);
        if (rst.next()) {
            return rst.getInt("documentCount");
        }
        return 0;
    }
    public String getNextDocumentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select Document_ID from Document order by Document_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("DT%04d", newIdIndex);
        }
        return "DT0001";
    }
    public ArrayList<DocumentDTO> getAllDocumentDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Document");

        ArrayList<DocumentDTO> documentDTOS = new ArrayList<>();

        while (rst.next()) {
            DocumentDTO documentDTO = new DocumentDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getString(6)
            );
            documentDTOS.add(documentDTO);
        }
        return documentDTOS;
    }

    public ArrayList<String> getStatusNames() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT DISTINCT Status FROM Document");

        ArrayList<String> statuses = new ArrayList<>();

        while (rst.next()) {
            statuses.add(rst.getString(1));
        }

        return statuses;
    }

    public ArrayList<DocumentDTO> getAllDocumentsByStatus(String selectedStatus) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Document WHERE Status = ?", selectedStatus);

        ArrayList<DocumentDTO> documentDTOS = new ArrayList<>();

        while (rst.next()) {
            DocumentDTO documentDTO = new DocumentDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getString(6)
            );
            documentDTOS.add(documentDTO);
        }
        return documentDTOS;
    }

    public boolean cancelDocument(String updateDocumentID) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Document SET Status = 'Cancel' WHERE Document_ID = ?";
        int rowsUpdated = SQLUtil.execute(query, updateDocumentID);
        return rowsUpdated > 0;
    }

    public boolean holdDocument(String updateDocumentID) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Document SET Status = 'Hold' WHERE Document_ID = ?";
        int rowsUpdated = SQLUtil.execute(query, updateDocumentID);
        return rowsUpdated > 0;
    }

    public DocumentDTO findById(String updateDocumentID) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Document WHERE Document_ID=?", updateDocumentID);

        if (rst.next()) {
            return new DocumentDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getString(6)
            );
        }
        return null;
    }
    public boolean saveDocument(DocumentDTO documentDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Document VALUES (?,?,?,?,?,?)",
                documentDTO.getDocumentId(),
                documentDTO.getProjectId(),
                documentDTO.getName(),
                documentDTO.getType(),
                documentDTO.getDateUploaded(),
                documentDTO.getStatus()
        );
    }
}
