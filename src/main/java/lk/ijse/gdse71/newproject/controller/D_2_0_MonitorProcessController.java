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
import lk.ijse.gdse71.newproject.dao.custom.ProjectDao;
import lk.ijse.gdse71.newproject.dao.custom.impl.ProjectDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class D_2_0_MonitorProcessController implements Initializable {

    private double ongoing;
    private double complete;
    private double planned;
    private double hold;
    private double cancel;

//    ProjectDaoImpl projectDao = new ProjectDaoImpl();
    ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getProjectValue();
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Failed to load project data. Please check the database connection.");
            return;
        }
        
        if (ongoing + complete + planned + hold + cancel == 0) {
            statusPieChart.setTitle("No project data available");
            return;
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Ongoing", ongoing),
                new PieChart.Data("Complete",complete),
                new PieChart.Data("Planned",planned),
                new PieChart.Data("Hold",hold),
                new PieChart.Data("Cancel",cancel)
        );

        pieChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName()," Projects : ",data.pieValueProperty())));

        statusPieChart.setTitle("Project Status Distribution");
        statusPieChart.getData().addAll(pieChartData);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    public void getProjectValue() throws SQLException {
      try{

          ongoing = projectDao.getProjectCountByStatus("Ongoing");
          complete = projectDao.getProjectCountByStatus("Completed");
          planned = projectDao.getProjectCountByStatus("Planned");
          hold = projectDao.getProjectCountByStatus("Hold");
          cancel = projectDao.getProjectCountByStatus("Cancel");

          if (ongoing < 0 || complete < 0 || planned < 0 || hold < 0 || cancel < 0) {
              throw new IllegalArgumentException("Project values cannot be negative!");
          }
      }catch (SQLException e) {
          e.printStackTrace();
          throw new SQLException("Error fetching project data from the database.", e);
      }
    }
    @FXML
    private AnchorPane DashBoard_Load_Panel;

    @FXML
    private PieChart statusPieChart;

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
}
