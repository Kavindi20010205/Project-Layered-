package lk.ijse.gdse71.newproject.DTO;
//import lombok.*;

import java.sql.Date;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class TaskDTO {
    private String taskId;
    private String projectId;
    private String employeeId;
    private String name;
    private Date deadline;

    public TaskDTO(String taskId, String projectId, String employeeId, String name, Date deadline) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.name = name;
        this.deadline = deadline;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public TaskDTO(){

    }
}
