package lk.ijse.gdse71.newproject.DTO;

public class ResourceDTO {
    private String resourceId;
    private String type;
    private String name;
    private int quantity;
    private String impact;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public ResourceDTO(String resourceId, String type, String name, int quantity, String impact) {
        this.resourceId = resourceId;
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.impact = impact;
    }
}
