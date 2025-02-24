package lk.ijse.gdse71.newproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class C_DashController {
    public AnchorPane DashBoard_Load_Panel;
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
    public void ManageProject_Navigate_OnAction(ActionEvent event){
        navigateTo("/view/D_1_0_ManageProjectFx.fxml");
    }
    public void MonitorProcess_Navigate_OnAction(ActionEvent event) {
        navigateTo("/view/D_2_0_MonitorProcessFx.fxml");
    }
    public void SubmitDesignProposalNavigate_OnAction(ActionEvent event)  {
        navigateTo("/view/D_3_0_SubmitDesignProposalFx.fxml");
    }
    public void TrackExpenses_Navigate_OnAction(ActionEvent event) throws IOException {
        navigateTo("/view/D_4_0_TrackExpensesFx.fxml");
    }
    public void AccessProjectDocuments_Navigate_OnAction(ActionEvent event) {
        navigateTo("/view/D_5_0_AccessProjectDocumentsFx.fxml");
    }
    public void UpdateProjectStatus_Navigate_OnAction(ActionEvent event)  {
        navigateTo("/view/D_6_0_FindProjectStatusFx.fxml");
    }
    public void ReviewFeedback_Navigate_OnAction(ActionEvent event){
        navigateTo("/view/D_7_0_ReviewFeedbackFx.fxml");
    }
    public void SubmitChangeRequest_Navigate_OnAction(ActionEvent event){
        navigateTo("/view/D_3_0_SubmitDesignProposalFx.fxml");
    }

}

