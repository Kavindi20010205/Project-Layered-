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
import lk.ijse.gdse71.newproject.DTO.DocumentDTO;
import lk.ijse.gdse71.newproject.DTO.tm.DocumentTM;
import lk.ijse.gdse71.newproject.dao.custom.DocumentDao;
import lk.ijse.gdse71.newproject.dao.custom.impl.DocumentDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class D_5_0_AccessProjectDocumentsController implements Initializable {
    public static String updateDocumentID;

//    DocumentModel documentModel = new DocumentModel();
//    DocumentDaoImpl documentDao = new DocumentDaoImpl();
    DocumentDao documentDao = new DocumentDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        documentID_col.setCellValueFactory(new PropertyValueFactory<>("documentId"));
        projectID_col.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("dateUploaded"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));

        holdDocument_btn.setDisable(true);
        cancelDocument_btn.setDisable(true);

        document_tbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean rowSelected = newValue != null;
            holdDocument_btn.setDisable(!rowSelected);
            cancelDocument_btn.setDisable(!rowSelected);
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
        loadTableData(documentDao.getAllDocumentDetails());
    }

    private void loadTableData(ArrayList<DocumentDTO> documents) {
        ObservableList<DocumentTM> documentTMS = FXCollections.observableArrayList();

        for (DocumentDTO documentDTO : documents) {
            DocumentTM documentTM = new DocumentTM(
                    documentDTO.getDocumentId(),
                    documentDTO.getProjectId(),
                    documentDTO.getName(),
                    documentDTO.getType(),
                    documentDTO.getDateUploaded(),
                    documentDTO.getStatus()
            );
            documentTMS.add(documentTM);
        }
        document_tbl.setItems(documentTMS);
    }

    private void loadStatus() throws SQLException {
        ArrayList<String> statusNames = documentDao.getStatusNames();
        ObservableList<String> observableList = FXCollections.observableArrayList(statusNames);
        statusFilter_Combox.setItems(observableList);
    }

    @FXML
    private AnchorPane DashBoard_Load_Panel;

    @FXML
    private Button cancelDocument_btn;

    @FXML
    private TableColumn<DocumentTM, String> date_col;

    @FXML
    private TableColumn<DocumentTM, String> documentID_col;

    @FXML
    private Button holdDocument_btn;

    @FXML
    private TableColumn<DocumentTM, String> name_col;

    @FXML
    private TableColumn<DocumentTM, String> projectID_col;

    @FXML
    private Label projectID_lbl;

    @FXML
    private TableView<DocumentTM> document_tbl;

    @FXML
    private ComboBox<String> statusFilter_Combox;

    @FXML
    private TableColumn<DocumentTM, String> status_col;

    @FXML
    private TableColumn<DocumentTM, String> type_col;

    @FXML
    void OnClickTable_MouseClickAction(MouseEvent event) {
        DocumentTM documentTM = document_tbl.getSelectionModel().getSelectedItem();
        if (documentTM != null) {
            projectID_lbl.setText(documentTM.getDocumentId());
        }
        updateDocumentID=projectID_lbl.getText();

        System.out.println(updateDocumentID);

    }

    @FXML
    void addNewDocument_btn_Navigate_OnAction(ActionEvent event) {
        navigateTo("/view/D_3_0_SubmitDesignProposalFx.fxml");

    }

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
    void cancelDocument_btn_Navigate_OnAction(ActionEvent event) throws SQLException {
        updateDocumentID = projectID_lbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this document?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isCancel = documentDao.cancelDocument(updateDocumentID);
            if (isCancel) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Project cancelled successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to cancel the project!").show();
            }
        }
    }

    @FXML
    void holdDocument_btn_Navigate_OnAction(ActionEvent event) throws SQLException {
        updateDocumentID = projectID_lbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to put this project on hold?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isHold = documentDao.holdDocument(updateDocumentID);
            if (isHold) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Project put on hold successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to put the project on hold!").show();
            }
        }

    }

    @FXML
    void refreshTable_btn_Navigate_OnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void statusFilter_Combox_OnAction(ActionEvent event) {
        projectID_lbl.setText("");
        String selectedStatus = statusFilter_Combox.getSelectionModel().getSelectedItem();
        if (selectedStatus != null) {
            try {
                ArrayList<DocumentDTO> filteredProjects = documentDao.getAllDocumentsByStatus(selectedStatus);
                loadTableData(filteredProjects);
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error filtering documents by status").show();
            }
        }
    }
}
