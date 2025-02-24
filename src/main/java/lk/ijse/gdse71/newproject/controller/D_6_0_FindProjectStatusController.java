package lk.ijse.gdse71.newproject.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.newproject.DTO.ProjectDTO;
import lk.ijse.gdse71.newproject.dao.custom.*;
import lk.ijse.gdse71.newproject.dao.custom.impl.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class D_6_0_FindProjectStatusController implements Initializable {
    public double total;
    public double spend;
    public double allocate;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshPage();
            getBudgetValue();
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Failed to load project data. Please check the database connection.");
            return;
        }

        if (total + spend + allocate== 0) {
            budget_pieChart.setTitle("No project data available");
            return;
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Total_Amount", total),
                new PieChart.Data("Allocate_Amount",allocate),
                new PieChart.Data("Spend_Amount",spend)
        );

        pieChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName()," Projects : ",data.pieValueProperty())));

        budget_pieChart.setTitle("Budget Status Distribution");
        budget_pieChart.getData().addAll(pieChartData);
    }

    private void refreshPage() throws SQLException {
        loadProjectId();
        projectID_comBox.getSelectionModel().clearSelection();
        projectName_lbl.setText("");
    }

    private void loadProjectId() throws SQLException {
        ArrayList<String> itemIds = projectDao.getAllProjectId();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        projectID_comBox.setItems(observableList);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }


    public void getBudgetValue() throws SQLException {
        try{
            total = budgetDao.getBudgetByStatus("Total_Amount");
            allocate = budgetDao.getBudgetByStatus("Allocate_Amount");
            spend = budgetDao.getBudgetByStatus("Spend_Amount");


            if (total < 0 || allocate < 0 || spend < 0 ) {
                throw new IllegalArgumentException("Budget values cannot be negative!");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching project data from the database.", e);
        }
    }

    private void updatePieChart() {
        budget_pieChart.getData().clear();

        if (total + spend + allocate == 0) {
            budget_pieChart.setTitle("No project data available");
            return;
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Total Amount", total),
                new PieChart.Data("Allocated Amount", allocate),
                new PieChart.Data("Spent Amount", spend)
        );

        pieChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " Projects: ", data.pieValueProperty())));

        budget_pieChart.setTitle("Budget Status Distribution");
        budget_pieChart.getData().setAll(pieChartData);

    }

    @FXML
    private Button back_btn;

    @FXML
    private PieChart budget_pieChart;

    @FXML
    private Label changeRequuestCompletePer_lbl;

    @FXML
    private Label completeMilestonePerc_lbl;

    @FXML
    private Label document_lbl;

    @FXML
    private Label employe_lbl;

    @FXML
    private Label meting_lbl;

    @FXML
    private AnchorPane DashBoard_Load_Panel;

    @FXML
    private ComboBox<String> projectID_comBox;

    @FXML
    private Label projectName_lbl;

    @FXML
    private Label riskHigh_lbl;

    @FXML
    private Label riskLow_lbl;

    @FXML
    private Label riskMedium_lbl;

//    private final BudgetModel budgetPie = new BudgetModel();
//    BudgetDaoImpl budgetDao = new BudgetDaoImpl();
    BudgetDao budgetDao = new BudgetDaoImpl();
//    private final ProjectModel projectModel = new ProjectModel();
//    ProjectDaoImpl projectDao = new ProjectDaoImpl();
    ProjectDao projectDao = new ProjectDaoImpl();
//    private final RiskModel riskModel = new RiskModel();
//    RiskDaoImpl riskDao = new RiskDaoImpl();
    RiskDao riskDao = new RiskDaoImpl();
//    private final Change_RequestModel changeReq = new Change_RequestModel();

//    ChangeRequestDaoImpl changeRequestDao = new ChangeRequestDaoImpl();
    ChangeRequestDao changeRequestDao = new ChangeRequestDaoImpl();
//    private final Project_AssignmentModel ProjectEmpl = new Project_AssignmentModel();
//    ProjectAssignmentDaoImpl projectAssignmentDao = new ProjectAssignmentDaoImpl();
    ProjectAssignmentDao projectAssignmentDao = new ProjectAssignmentDaoImpl();
//    private final MeetingModel meeting = new MeetingModel();
//    MeetingDaoImpl meetingDao = new MeetingDaoImpl();
    MeetingDao meetingDao = new MeetingDaoImpl();
//    private final DocumentModel document = new DocumentModel();
//    DocumentDaoImpl documentDao = new DocumentDaoImpl();
    DocumentDao documentDao = new DocumentDaoImpl();
//    private final MilestoneModel milestones = new MilestoneModel();
//    MilestoneDaoImpl milestoneDao = new MilestoneDaoImpl();
    MilestoneDao milestoneDao = new MilestoneDaoImpl();


    @FXML
    void back_btn_Navigate_OnAction(ActionEvent event) {
        navigateTo("/view/C_DashFx.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            DashBoard_Load_Panel.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(DashBoard_Load_Panel.widthProperty());
            load.prefHeightProperty().bind(DashBoard_Load_Panel.heightProperty());

            DashBoard_Load_Panel.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @FXML
    void projectID_comBox_OnAction(ActionEvent event) throws SQLException {
        String selectedProjectId = projectID_comBox.getSelectionModel().getSelectedItem();

        ProjectDTO projectdTO = projectDao.findById(selectedProjectId);

        if(projectdTO != null){
            projectName_lbl.setText(projectdTO.getName());
        } else {
            projectName_lbl.setText("Project not found");
        }

        int meetingCount = meetingDao.getProjectAllMeetingCount(selectedProjectId);
        meting_lbl.setText(String.valueOf(meetingCount));

        int employeeCount = projectAssignmentDao.getProjectAllEmployeeCount(selectedProjectId);
        employe_lbl.setText(String.valueOf(employeeCount));

        double changeCount = changeRequestDao.getProjectChangeRequestCount(selectedProjectId);
        changeRequuestCompletePer_lbl.setText(String.valueOf(changeCount));

        double mileCount = milestoneDao.getProjectMilestoneCount(selectedProjectId);
        completeMilestonePerc_lbl.setText(String.valueOf(mileCount));

        int documentCount = documentDao.getProjectDocumentCount(selectedProjectId);
        document_lbl.setText(String.valueOf(documentCount));

        int highRiskCount = riskDao.getProjectRiskCount(selectedProjectId,"High");
        riskHigh_lbl.setText(String.valueOf(highRiskCount));

        int mediumRiskCount = riskDao.getProjectRiskCount(selectedProjectId,"Medium");
        riskMedium_lbl.setText(String.valueOf(mediumRiskCount));

        int lowRiskCount = riskDao.getProjectRiskCount(selectedProjectId,"Low");
        riskLow_lbl.setText(String.valueOf(lowRiskCount));

        newBudgetvalues(selectedProjectId);
    }

    private void newBudgetvalues(String selectedProjectId) throws SQLException {
        try{

            total = budgetDao.getProjectBudgetByStatus("Total_Amount", selectedProjectId);
            allocate = budgetDao.getProjectBudgetByStatus("Allocate_Amount",selectedProjectId);
            spend = budgetDao.getProjectBudgetByStatus("Spend_Amount",selectedProjectId);

            if (total < 0 || allocate < 0 || spend < 0 ) {
                throw new IllegalArgumentException("Budget values cannot be negative!");
            }
            updatePieChart();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching project data from the database.", e);
        }
    }
}
