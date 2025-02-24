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
import lk.ijse.gdse71.newproject.DTO.FeedbackDTO;
import lk.ijse.gdse71.newproject.DTO.ProjectDTO;
import lk.ijse.gdse71.newproject.DTO.tm.FeedbackTM;
import lk.ijse.gdse71.newproject.dao.custom.FeedbackDao;
import lk.ijse.gdse71.newproject.dao.custom.ProjectDao;
import lk.ijse.gdse71.newproject.dao.custom.impl.FeedbackDaoImpl;
import lk.ijse.gdse71.newproject.dao.custom.impl.ProjectDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class D_7_0_ReviewFeedbackController implements Initializable {

    @FXML
    private Label clientID_lbl;
    @FXML
    private AnchorPane DashBoard_Load_Panel;

    @FXML
    private Button back_btn, edit_btn, save_btn, refresh_btn;

    @FXML
    private Label  feedbackID_lbl, date_lbl;

    @FXML
    private TextArea comment_txt;

    @FXML
    private TextField rating_txt;

    @FXML
    private ComboBox<String> projectID_comBox;

    @FXML
    private TableView<FeedbackTM> feedback_tbl;

    @FXML
    private TableColumn<FeedbackTM, String> feedback_col, project_col, client_col, date_col, comment_col, rating_col;

//    private final FeedbackModel feedbackModel = new FeedbackModel();
//    FeedbackDaoImpl feedbackDao = new FeedbackDaoImpl();
    FeedbackDao feedbackDao = new FeedbackDaoImpl();
//    private final ProjectModel projectModel = new ProjectModel();
//    ProjectDaoImpl projectDao = new ProjectDaoImpl();
    ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date_lbl.setText(LocalDate.now().toString());

        feedback_col.setCellValueFactory(new PropertyValueFactory<>("feedbackId"));
        project_col.setCellValueFactory(new PropertyValueFactory<>("productId"));
        client_col.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        comment_col.setCellValueFactory(new PropertyValueFactory<>("comment"));
        rating_col.setCellValueFactory(new PropertyValueFactory<>("rating"));

        try {
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to initialize feedback table.").show();
        }
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
    void edit_btn_OnAction(ActionEvent event) {
        try {
            FeedbackDTO feedbackDTO = collectFeedbackData();
            boolean isUpdated = feedbackDao.updateFeedback(feedbackDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Feedback updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update feedback.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Invalid input for editing feedback.").show();
        }
    }

    @FXML
    void feedbacktable_mouceClickOnAction(MouseEvent event) {
        FeedbackTM selectedFeedback = feedback_tbl.getSelectionModel().getSelectedItem();
        if (selectedFeedback != null) {
            feedbackID_lbl.setText(selectedFeedback.getFeedbackId());
            projectID_comBox.setValue(selectedFeedback.getProductId());
            clientID_lbl.setText(selectedFeedback.getClientId());
            date_lbl.setText(selectedFeedback.getDate().toString());
            comment_txt.setText(selectedFeedback.getComment());
            rating_txt.setText(String.valueOf(selectedFeedback.getRating()));

            save_btn.setDisable(true);
            edit_btn.setDisable(false);
            
            refresh_btn.setDisable(false);
        }
    }

    @FXML
    void refresh_btn_On_Action(ActionEvent event) {
        try {
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void save_btn_OnAction(ActionEvent event) throws SQLException {
        String comment= comment_txt.getText();
        String rating = rating_txt.getText();
        String projectID = projectID_comBox.getSelectionModel().getSelectedItem();

        if (projectID == null || projectID.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please select project ID !").show();
            return;
        }
        if (comment == null || comment.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please insert comment !").show();
            return;
        }
        if (rating == null || rating.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please insert rating !").show();
            return;
        }

        String ratingPatter = "[0-5]";
        boolean isValidRating = rating.matches(ratingPatter);

        if(!isValidRating){
            rating_txt.setStyle(rating_txt.getStyle()+";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid rating !").show();
            return;
        } else{
            rating_txt.setStyle(null);
        }
        
        try {
            FeedbackDTO feedbackDTO = collectFeedbackData();
            boolean isSaved = feedbackDao.saveFeedback(feedbackDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Feedback saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save feedback.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Invalid input for saving feedback.").show();
        }
    }

    private FeedbackDTO collectFeedbackData() throws SQLException {

        String feedbackId = feedbackID_lbl.getText();
        String projectId = projectID_comBox.getValue();
        String clientId = projectDao.findById(projectId).getClientId();
        Date feedbackDate = Date.valueOf(date_lbl.getText());
        String comment = comment_txt.getText();
        int rating = Integer.parseInt(rating_txt.getText());


        return new FeedbackDTO(feedbackId, projectId, clientId, feedbackDate, comment, rating);
    }

    private void refreshPage() throws SQLException {
        rating_txt.setStyle(null);

        loadNextFeedbackId();
        loadTableData();
        loadProjectIds();
        clearInputs();
    }

    private void loadNextFeedbackId() throws SQLException {
        String nextFeedbackId = feedbackDao.getNextFeedbackId();
        feedbackID_lbl.setText(nextFeedbackId);
    }

    private void loadTableData() throws SQLException {
        ArrayList<FeedbackDTO> feedbackList = feedbackDao.getAllFeedbacks();
        ObservableList<FeedbackTM> observableList = FXCollections.observableArrayList();
        for (FeedbackDTO feedbackDTO : feedbackList) {
            observableList.add(new FeedbackTM(
                    feedbackDTO.getFeedbackId(),
                    feedbackDTO.getProductId(),
                    feedbackDTO.getClientId(),
                    feedbackDTO.getDate(),
                    feedbackDTO.getComment(),
                    feedbackDTO.getRating()
            ));
        }
        feedback_tbl.setItems(observableList);
    }

    private void loadProjectIds() throws SQLException {
        ObservableList<String> projectIds = FXCollections.observableArrayList(projectDao.getAllProjectId());
        projectID_comBox.setItems(projectIds);
    }

    private void clearInputs() {
        projectID_comBox.getSelectionModel().clearSelection();
        comment_txt.clear();
        rating_txt.clear();
        clientID_lbl.setText("");
       // feedbackID_lbl.setText(""); ss eka ganna witrak
        date_lbl.setText(LocalDate.now().toString());
    }

    public void projectID_comBox_OnAction(ActionEvent event) throws SQLException {
        String selectedItemId = projectID_comBox.getSelectionModel().getSelectedItem();
        ProjectDTO projectDTO = projectDao.findById(selectedItemId);

        if (projectDTO != null) {
            clientID_lbl.setText(projectDTO.getClientId());
        }
    }
}
