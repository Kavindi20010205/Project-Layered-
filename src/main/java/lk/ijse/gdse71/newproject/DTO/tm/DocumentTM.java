package lk.ijse.gdse71.newproject.DTO.tm;

import java.sql.Date;

public class DocumentTM {

    private String documentId;
    private String projectId;
    private String name;
    private String type;
    private Date dateUploaded;
    private String status;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DocumentTM(String documentId, String projectId, String name, String type, Date dateUploaded, String status) {
        this.documentId = documentId;
        this.projectId = projectId;
        this.name = name;
        this.type = type;
        this.dateUploaded = dateUploaded;
        this.status = status;
    }
}
