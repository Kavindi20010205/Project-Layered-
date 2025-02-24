package lk.ijse.gdse71.newproject.DTO;

import lk.ijse.gdse71.newproject.DTO.tm.EmployeeTM;

//import lombok.*;
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class EmployeeDTO {
    private String employeeId;
    private String name;
    private String departmentId;
    private String role;
    private String contact;
    private String email;

    public EmployeeDTO(EmployeeTM selectedEmployee) {
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAssignProjects() {
        return assignProjects;
    }

    public void setAssignProjects(int assignProjects) {
        this.assignProjects = assignProjects;
    }

    private String address;

    public EmployeeDTO(String employeeId, String name, String departmentId, String role, String contact, String email, String address, int assignProjects) {
        this.employeeId = employeeId;
        this.name = name;
        this.departmentId = departmentId;
        this.role = role;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.assignProjects = assignProjects;
    }

    private int assignProjects;
}
