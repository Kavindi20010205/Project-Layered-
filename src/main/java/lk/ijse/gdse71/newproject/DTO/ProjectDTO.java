package lk.ijse.gdse71.newproject.DTO;

//import lombok.*;

import java.sql.Date;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString

public class ProjectDTO {
    private String projectId;
    private String name;
    private String clientId;
    private String serviceId;
    private Date startDate;
    private Date endDate;
    private String location;
    private String address;
    private String status;

    public ProjectDTO(String projectId, String name, String clientId, String serviceId, Date startDate, Date endDate, String location, String address, String status) {
        this.projectId = projectId;
        this.name = name;
        this.clientId = clientId;
        this.serviceId = serviceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.address = address;
        this.status = status;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProjectDTO() {

    }

}
