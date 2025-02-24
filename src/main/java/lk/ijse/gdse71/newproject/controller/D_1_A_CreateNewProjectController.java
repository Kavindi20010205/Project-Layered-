package lk.ijse.gdse71.newproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.newproject.DTO.ClientDTO;
import lk.ijse.gdse71.newproject.DTO.ProjectDTO;
import lk.ijse.gdse71.newproject.DTO.ServiceDTO;
import lk.ijse.gdse71.newproject.dao.custom.ClientDao;
import lk.ijse.gdse71.newproject.dao.custom.ProjectDao;
import lk.ijse.gdse71.newproject.dao.custom.ServiceDao;
import lk.ijse.gdse71.newproject.dao.custom.impl.ClientDaoImpl;
import lk.ijse.gdse71.newproject.dao.custom.impl.ProjectDaoImpl;
import lk.ijse.gdse71.newproject.dao.custom.impl.ServiceDaoImpl;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class D_1_A_CreateNewProjectController {

//    ProjectModel projectModel = new ProjectModel();
//    ProjectDaoImpl projectDao = new ProjectDaoImpl();
    ProjectDao projectDao = new ProjectDaoImpl();
//    ClientModel clientModel = new ClientModel();
//    ClientDaoImpl clientDao = new ClientDaoImpl();
    ClientDao clientDao = new ClientDaoImpl();
//    ServiceModel serviceModel = new ServiceModel();
//    ServiceDaoImpl serviceDao = new ServiceDaoImpl();
    ServiceDao serviceDao = new ServiceDaoImpl();

    public void initialize() {
        location_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            location_txt.setStyle(null);
        });

        save_btn.setDisable(false);
        refresh_btn.setDisable(false);

        try {
            refreshPage();
        } catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to Load Page !");

        }
    }

    private void refreshPage() throws Exception{
        loadNextProjectId();
        loadClientIds();
        loadServiceIds();

        System.out.println("New Project Loader Check !");

        service_id_combox.getSelectionModel().clearSelection();
        client_id_combox.getSelectionModel().clearSelection();

        address_txt.setText("");
        clientName_label.setText("");
        location_txt.setText("");
        project_txt.setText("");
        serviceName_label.setText("");

        startDate_picker.getEditor().clear();
        startDate_picker.setValue(null);

        endDate_picker.getEditor().clear();
        endDate_picker .setValue(null);
    }

    private void loadServiceIds() throws SQLException {
        ArrayList<String> serviceIds = serviceDao.getAllServiceIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(serviceIds);
        service_id_combox.setItems(observableList);
    }

    private void loadClientIds() throws SQLException {
        ArrayList<String> clientids = clientDao.getAllClientIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(clientids);
        client_id_combox.setItems(observableList);
    }

    private void loadNextProjectId() throws SQLException {
        String nextProjectId = projectDao.getNextProjectId();
        project_ID_label.setText(nextProjectId);
    }

    @FXML
    private AnchorPane DashBoard_Load_Panel;

    @FXML
    private TextField address_txt;

    @FXML
    private Button back_btn;

    @FXML
    private Label clientName_label;

    @FXML
    private ComboBox<String> client_id_combox;

    @FXML
    private DatePicker endDate_picker;

    @FXML
    private TextField location_txt;

    @FXML
    private Label project_ID_label;

    @FXML
    private TextField project_txt;

    @FXML
    private Button refresh_btn;

    @FXML
    private Button save_btn;

    @FXML
    private Label serviceName_label;

    @FXML
    private ComboBox<String> service_id_combox;

    @FXML
    private DatePicker startDate_picker;

    @FXML
    private Label ststus_label;

    @FXML
    void back_btn_Navigate_OnAction(ActionEvent event) {
        navigateTo("/view/D_1_0_ManageProjectFx.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try{
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
    void client_id_combox_onAction(ActionEvent event) throws SQLException {
        String selectedClientId = client_id_combox.getSelectionModel().getSelectedItem();
        ClientDTO clientDTO = clientDao.findById(selectedClientId);

        if (clientDTO != null) {
            clientName_label.setText(clientDTO.getName());
        }
    }

    @FXML
    void refresh_btn_OnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void save_btn_OnAction(ActionEvent event) throws Exception{
        String projectId = project_ID_label.getText();
        String projectName = project_txt.getText();
        String clientId = client_id_combox.getSelectionModel().getSelectedItem();
        String serviceId = service_id_combox.getSelectionModel().getSelectedItem();
        String location = location_txt.getText();
        String address = address_txt.getText();
        String status = "Planned";

        Date startDate = null;
        Date endDate = null;
//        Date startDate = Date.valueOf(startDate_picker.getValue());
//        Date endDate = Date.valueOf(endDate_picker.getValue());
        if (startDate_picker.getValue() != null) {
            startDate = Date.valueOf(startDate_picker.getValue());
        }

        if (endDate_picker.getValue() != null) {
            endDate = Date.valueOf(endDate_picker.getValue());
        }

        if (startDate != null && endDate != null && endDate.before(startDate)) {
            new Alert(Alert.AlertType.ERROR, "End Date cannot be before Start Date!").show();
            return;
        }
        if(endDate != null) {
            if (endDate.before(startDate)) {
                new Alert(Alert.AlertType.ERROR, "End Date cannot be before Start Date!").show();
                return;
            }
        }

        if (clientId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Client ID !").show();
            return;
        }
        if (serviceId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Service ID !").show();
            return;
        }
        if (location == null || location.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please insert Location !").show();
            return;
        }
        if (projectName == null || projectName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please insert Project Name !").show();
            return;
        }

        String locationNotPattern = ".*\\d.*";
        boolean isValidLocation = !location.matches(locationNotPattern);

        if (!isValidLocation) {
            location_txt.setStyle(location_txt.getStyle()+";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid Location !").show();
            return;
        } else{
            location_txt.setStyle(null);
        }

        if (isValidLocation){
            ProjectDTO projectDTO = new ProjectDTO(
                    projectId,
                    projectName,
                    clientId,
                    serviceId,
                    startDate,
                    endDate,
                    location,
                    address,
                    status
            );
            boolean isSaved = projectDao.saveProject(projectDTO);
            if (isSaved){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION,"Project Saved !").show();
            } else {
                new Alert(Alert.AlertType.ERROR,"Fail to save Project !").show();
            }
        }
    }

    @FXML
    void service_id_combox_OnAction(ActionEvent event) throws SQLException {
        String selectedServiceId = service_id_combox.getSelectionModel().getSelectedItem();
        ServiceDTO serviceDTO = serviceDao.findById(selectedServiceId);

        if(serviceDTO != null){
            serviceName_label.setText(serviceDTO.getName());
        }
    }
}
