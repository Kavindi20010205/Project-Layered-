package lk.ijse.gdse71.newproject.DTO.tm;

public class BudgetTM {
    private String budgetId;
    private String projectId;
    private String currency;
    private double totalAmount;
    private double allocateAmount;
    private double spendAmount;

    public String getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getAllocateAmount() {
        return allocateAmount;
    }

    public void setAllocateAmount(double allocateAmount) {
        this.allocateAmount = allocateAmount;
    }

    public double getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(double spendAmount) {
        this.spendAmount = spendAmount;
    }

    public BudgetTM(String budgetId, String projectId, String currency, double totalAmount, double allocateAmount, double spendAmount) {
        this.budgetId = budgetId;
        this.projectId = projectId;
        this.currency = currency;
        this.totalAmount = totalAmount;
        this.allocateAmount = allocateAmount;
        this.spendAmount = spendAmount;
    }
}
