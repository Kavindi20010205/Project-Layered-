package lk.ijse.gdse71.newproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.newproject.DTO.tm.ProjectTM;
import lk.ijse.gdse71.newproject.DTO.ProjectDTO;
import lk.ijse.gdse71.newproject.dao.custom.ProjectDao;
import lk.ijse.gdse71.newproject.dao.custom.impl.ProjectDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class D_1_0_ManageProjectController implements Initializable {
public static String updateProjectId;
    @FXML
    private Label projectID_lbl;

    @FXML
    private TableView<ProjectTM> project_tbl;

    @FXML
    private ComboBox<String> statusFilter_Combox;

    @FXML
    private AnchorPane DashBoard_Load_Panel;

    @FXML
    private Button back_btn;

    @FXML
    private Button cancelProject_btn;

    @FXML
    private Button createNewProject_btn;

    @FXML
    private Button holdProject_btn;

    @FXML
    private Button updateProject_btn;

    @FXML
    private TableColumn<ProjectTM, String> status_col;

    @FXML
    private TableColumn<ProjectTM, String> address_col;

    @FXML
    private TableColumn<ProjectTM, String> clientID_col;

    @FXML
    private TableColumn<ProjectTM, String> endDate_col;

    @FXML
    private TableColumn<ProjectTM, String> location_col;

    @FXML
    private TableColumn<ProjectTM, String> projectID_col;

    @FXML
    private TableColumn<ProjectTM, String> projectName_col;

    @FXML
    private TableColumn<ProjectTM, String> serviceID_col;

    @FXML
    private TableColumn<ProjectTM, String> startDate_col;

    public static String passProjectID = null;

//    ProjectModel projectModel = new ProjectModel();
//    ProjectDaoImpl projectDao = new ProjectDaoImpl();
    ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        projectID_col.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        projectName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientID_col.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        serviceID_col.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        startDate_col.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate_col.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));

        updateProject_btn.setDisable(true);
        holdProject_btn.setDisable(true);
        cancelProject_btn.setDisable(true);

        project_tbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean rowSelected = newValue != null;
            updateProject_btn.setDisable(!rowSelected);
            holdProject_btn.setDisable(!rowSelected);
            cancelProject_btn.setDisable(!rowSelected);
        });

        try {
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load projects").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadStatus();
        statusFilter_Combox.getSelectionModel().clearSelection();
        projectID_lbl.setText("");
        loadTableData(projectDao.getAllProjectDetails());
    }

    private void loadStatus() throws SQLException {
        ArrayList<String> statusNames = projectDao.getStatusNames();
        ObservableList<String> observableList = FXCollections.observableArrayList(statusNames);
        statusFilter_Combox.setItems(observableList);
    }

    public void createNewProject_btn_Navigate_OnAction(ActionEvent event) {

        navigateTo("/view/D_1_A_CreateNewProjectFx.fxml");
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
            new Alert(Alert.AlertType.ERROR, "Failed to load page").show();
        }
    }

    public void updateProject_btn_Navigate_OnAction(ActionEvent event) {
        passProjectID = projectID_lbl.getText();
        navigateTo("/view/D_1_B_UpdateProjectFx.fxml");
    }

    public void holdProject_btn_Navigate_OnAction(ActionEvent event) throws SQLException {
        passProjectID = projectID_lbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to put this project on hold?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isHold = projectDao.holdProject(passProjectID);
            if (isHold) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Project put on hold successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to put the project on hold!").show();
            }
        }
    }

    public void cancelProject_btn_Navigate_OnAction(ActionEvent event) throws SQLException {
        passProjectID = projectID_lbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this project?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isCancel = projectDao.cancelProject(passProjectID);
            if (isCancel) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Project cancelled successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to cancel the project!").show();
            }
        }
    }

    public void back_btn_Navigate_OnAction(ActionEvent event) {
        navigateTo("/view/C_DashFx.fxml");
    }

    public void statusFilter_Combox_OnAction(ActionEvent event) {
        projectID_lbl.setText("");
        String selectedStatus = statusFilter_Combox.getSelectionModel().getSelectedItem();
        if (selectedStatus != null) {
            try {
                ArrayList<ProjectDTO> filteredProjects = projectDao.getProjectsByStatus(selectedStatus);
                loadTableData(filteredProjects);
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error filtering projects by status").show();
            }
        }
    }

    private void loadTableData(ArrayList<ProjectDTO> projects) {
        ObservableList<ProjectTM> projectTMs = FXCollections.observableArrayList();

        for (ProjectDTO projectDTO : projects) {
            ProjectTM projectTM = new ProjectTM(
                    projectDTO.getProjectId(),
                    projectDTO.getName(),
                    projectDTO.getClientId(),
                    projectDTO.getServiceId(),
                    projectDTO.getStartDate(),
                    projectDTO.getEndDate(),
                    projectDTO.getLocation(),
                    projectDTO.getAddress(),
                    projectDTO.getStatus()
            );
            projectTMs.add(projectTM);
        }
        project_tbl.setItems(projectTMs);
    }

    public void OnClickTable_MouseClickAction(MouseEvent mouseEvent) {
        ProjectTM projectTM = project_tbl.getSelectionModel().getSelectedItem();
        if (projectTM != null) {
            projectID_lbl.setText(projectTM.getProjectId());
        }
        updateProjectId=projectID_lbl.getText();

        System.out.println(updateProjectId);

    }

    public void refreshTable_btn_Navigate_OnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }
}
