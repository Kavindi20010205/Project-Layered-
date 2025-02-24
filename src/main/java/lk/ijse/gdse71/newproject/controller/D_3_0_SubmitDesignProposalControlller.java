package lk.ijse.gdse71.newproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.newproject.DTO.DocumentDTO;
import lk.ijse.gdse71.newproject.DTO.ProjectDTO;
import lk.ijse.gdse71.newproject.DTO.ServiceDTO;
import lk.ijse.gdse71.newproject.dao.custom.DocumentDao;
import lk.ijse.gdse71.newproject.dao.custom.ProjectDao;
import lk.ijse.gdse71.newproject.dao.custom.ServiceDao;
import lk.ijse.gdse71.newproject.dao.custom.impl.DocumentDaoImpl;
import lk.ijse.gdse71.newproject.dao.custom.impl.ProjectDaoImpl;
import lk.ijse.gdse71.newproject.dao.custom.impl.ServiceDaoImpl;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class D_3_0_SubmitDesignProposalControlller {

//    ProjectModel projectModel = new ProjectModel();
//    ProjectDaoImpl projectDao = new ProjectDaoImpl();
    ProjectDao projectDao = new ProjectDaoImpl();
//    ServiceModel serviceModel = new ServiceModel();
//    ServiceDaoImpl serviceDao = new ServiceDaoImpl();
    ServiceDao serviceDao = new ServiceDaoImpl();
//    DocumentModel documentModel = new DocumentModel();
//    DocumentDaoImpl documentDao = new DocumentDaoImpl();
    DocumentDao documentDao = new DocumentDaoImpl();


    public void initialize() {
       // save_btn.setDisable(true);
       try {
            refreshPage();
       } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Page").show();
       }
    }
    public void refreshPage() throws Exception {
        loadProjectIds();
        loadNextDocumentId();

        setToday_lbl.setText(LocalDate.now().toString());

        System.out.println("loaders checked !");

        type_combox.getSelectionModel().clearSelection();
        projectID_combox.getSelectionModel().clearSelection();

        type_combox.getItems().addAll("Blueprint", "Design Document", "Report", "Contract", "Financial Document", "Technical Document");

        project_ID_lbl.setText("");
        projectName_lbl.setText("");
        service_ID_label.setText("");
        servicename_lbl.setText("");
        documentName_txt.setText("");
    }

    private void loadNextDocumentId() throws SQLException {
        String nextDocumentId = documentDao.getNextDocumentId();
        document_ID_lbl.setText(nextDocumentId);
    }

    private void loadProjectIds() throws SQLException {
        ArrayList<String> customerIds = projectDao.getAllProjectIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        projectID_combox.setItems(observableList);

    }

    @FXML
    private AnchorPane DashBoard_Load_Panel;

    @FXML
    private Button back_btn;

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField documentName_txt;

    @FXML
    private Label document_ID_lbl;

    @FXML
    private ComboBox<String> projectID_combox;

    @FXML
    private Label projectName_lbl;

    @FXML
    private Label project_ID_lbl;

    @FXML
    private Button save_btn;

    @FXML
    private Button search_btn;

    @FXML
    private Label service_ID_label;

    @FXML
    private Label servicename_lbl;

    @FXML
    private Label setToday_lbl;

    @FXML
    private TextField status_txt;

    @FXML
    private ComboBox<String> type_combox;

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
            new Alert(Alert.AlertType.ERROR, "Failed to load page").show();
        }
    }

    @FXML
    void cancel_btn_OnAction(ActionEvent event) throws Exception {
        refreshPage();
    }


    @FXML
    void projectID_combox_OnAction(ActionEvent event) throws SQLException {
        String selectedProjectId = projectID_combox.getSelectionModel().getSelectedItem();
        ProjectDTO projectDTO = projectDao.findById(selectedProjectId);


        if (projectDTO != null) {
            project_ID_lbl.setText(projectDTO.getProjectId());
            projectName_lbl.setText(projectDTO.getName());
            service_ID_label.setText(projectDTO.getServiceId());

        }

        ServiceDTO serviceDTO = serviceDao.findById(service_ID_label.getText());

        if (serviceDTO != null) {
            servicename_lbl.setText(serviceDTO.getName());
        }
    }

    @FXML
    void save_btn_OnAction(ActionEvent event) throws Exception {
        String documentId = document_ID_lbl.getText();
        String projectId = projectID_combox.getSelectionModel().getSelectedItem();
        String documentName = documentName_txt.getText();
        String type = type_combox.getSelectionModel().getSelectedItem();
        Date date = Date.valueOf(setToday_lbl.getText());
        String status = "Pending";

        if (projectId == null || projectId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select Project ID !").show();
            return;
        }
        if (documentName == null || documentName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please insert Document Name !").show();
            return;
        }
        if (type == null || type.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select Document Type !").show();
            return;
        }
        DocumentDTO documentDTO = new DocumentDTO(
                documentId,
                projectId,
                documentName,
                type,
                date,
                status
        );
        boolean isSaved = documentDao.saveDocument(documentDTO);
        if (isSaved){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,"Document Saved ! ").show();
        } else {
            new Alert(Alert.AlertType.ERROR,"Fail to save Document !").show();
        }
    }
}