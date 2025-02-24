package lk.ijse.gdse71.newproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.newproject.DTO.EmployeeDTO;
import lk.ijse.gdse71.newproject.dao.custom.EmployeeDao;
import lk.ijse.gdse71.newproject.dao.custom.impl.EmployeeDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static lk.ijse.gdse71.newproject.controller.A_LoginController.employeeID;

public class B_DashBoarddController implements Initializable {

    public Label date_lbl;
    @FXML
    private Label id_lbl;
    @FXML
    private Label name_lbl;
    @FXML
    private Label role_lbl;

    public AnchorPane panel_loading_panel;
    public AnchorPane mainAnchor_panel;
    public AnchorPane mainMainAnchor_Panel;

//    EmployeeModel employeeModel= new EmployeeModel();
//    EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        date_lbl.setText(String.valueOf(LocalDate.now()));
        try{
            if(employeeID!=null){
                id_lbl.setText(employeeID);
                EmployeeDTO employeeDTO = employeeDao.findById(employeeID);
                if (employeeDTO != null) {
                    role_lbl.setText(employeeDTO.getRole());
                    name_lbl.setText(employeeDTO.getName());
                }
            }else {
                new Alert(Alert.AlertType.WARNING, "Employee ID is null!").show();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to fetch employee details!").show();
        }
        navigateTo("/view/C_DashFx.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            panel_loading_panel.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(panel_loading_panel.widthProperty());
            load.prefHeightProperty().bind(panel_loading_panel.heightProperty());

            panel_loading_panel.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    public void home_btn_Navigate_OnAction(ActionEvent event) {
        navigateTo("/view/C_HomeFx.fxml");
    }

    public void dashboard_btn_Navigate_OnAction(ActionEvent event) {
        navigateTo("/view/C_DashFx.fxml");
    }

    public void logout_btn_OnAction(ActionEvent event) throws IOException {
        mainAnchor_panel.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/A_LoginFx.fxml"));
        mainAnchor_panel.getChildren().add(load);
    }
}
