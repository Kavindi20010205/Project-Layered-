package lk.ijse.gdse71.newproject.DTO;
//import lombok.*;
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class ClientDTO {
    private String clientId;
    private String name;
    private String contact;
    private String email;
    private String address;

    public ClientDTO(String clientId, String name, String contact, String email, String address) {
        this.clientId = clientId;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.address = address;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ClientDTO(){

    }
}
