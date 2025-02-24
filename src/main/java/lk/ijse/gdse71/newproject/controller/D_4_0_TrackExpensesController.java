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
import lk.ijse.gdse71.newproject.DTO.BudgetDTO;
import lk.ijse.gdse71.newproject.DTO.ProjectDTO;
import lk.ijse.gdse71.newproject.DTO.tm.BudgetTM;
import lk.ijse.gdse71.newproject.dao.custom.BudgetDao;
import lk.ijse.gdse71.newproject.dao.custom.ProjectDao;
import lk.ijse.gdse71.newproject.dao.custom.impl.BudgetDaoImpl;
import lk.ijse.gdse71.newproject.dao.custom.impl.ProjectDaoImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class D_4_0_TrackExpensesController implements Initializable {

    @FXML
    private AnchorPane DashBoard_Load_Panel;

    @FXML
    private TableColumn<BudgetTM, Double> allocateAmount_col;

    @FXML
    private TextField allocateAmount_txt;

    @FXML
    private Button back_btn;

    @FXML
    private TableColumn<BudgetTM, String> budgetID_col;

    @FXML
    private Label budget_ID_label;

    @FXML
    private TableView<BudgetTM> budget_tbl;

    @FXML
    private TableColumn<BudgetTM, Double> currency_col;
    @FXML
    private Label project_label_lbl;
    @FXML
    private TextField currency_txt;

    @FXML
    private Button delete_btn;

    @FXML
    private TableColumn<BudgetTM, String> projectID_col;

    @FXML
    private ComboBox<String> projectID_combox;

    @FXML
    private Button reset_btn;

    @FXML
    private Button save_btn;

    @FXML
    private TableColumn<BudgetTM, Double> spendAmount_col;

    @FXML
    private TextField spendAmount_txt;

    @FXML
    private TableColumn<BudgetTM, Double> totalAmount_col;

    @FXML
    private TextField totalAmount_txt;

    @FXML
    private Button update_btn;

//    BudgetModel budgetModel = new BudgetModel();
//    BudgetDaoImpl budgetDao = new BudgetDaoImpl();
    BudgetDao budgetDao = new BudgetDaoImpl();
//    ProjectModel projectModel = new ProjectModel();
//    ProjectDaoImpl projectDao = new ProjectDaoImpl();
    ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load track id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        budget_ID_label.setText(budgetDao.getNextId());
        loadTableData();
        loadProjectrIds();

        save_btn.setDisable(false);

        update_btn.setDisable(true);
        delete_btn.setDisable(true);

        projectID_combox.getSelectionModel().clearSelection();
        currency_txt.setText("");
        totalAmount_txt.setText("");
        allocateAmount_txt.setText("");
        spendAmount_txt.setText("");
        project_label_lbl.setText("");

    }

    private void loadProjectrIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> projectIds = projectDao.getAllProjectIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(projectIds);
        projectID_combox.setItems(observableList);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<BudgetDTO> budgetDTOS = budgetDao.getAll();
        ObservableList<BudgetTM> budgetTMS = FXCollections.observableArrayList();

        for (BudgetDTO budgetDTO : budgetDTOS){
            BudgetTM budgetTM = new BudgetTM(
                    budgetDTO.getBudgetId(),
                    budgetDTO.getProjectId(),
                    budgetDTO.getCurrency(),
                    budgetDTO.getTotalAmount(),
                    budgetDTO.getAllocateAmount(),
                    budgetDTO.getSpendAmount()
            );
            budgetTMS.add(budgetTM);
        }
        budget_tbl.setItems(budgetTMS);
    }

    private void setCellValues() {
        budgetID_col.setCellValueFactory(new PropertyValueFactory<>("budgetId"));
        projectID_col.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        currency_col.setCellValueFactory(new PropertyValueFactory<>("currency"));
        totalAmount_col.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        allocateAmount_col.setCellValueFactory(new PropertyValueFactory<>("allocateAmount"));
        spendAmount_col.setCellValueFactory(new PropertyValueFactory<>("spendAmount"));

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
    void delete_btn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String budgetId = budget_ID_label.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = budgetDao.delete(budgetId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Budget deleted !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Budget !").show();
            }
        }
    }

    @FXML
    void reset_btn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void save_btn_ONAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String budgetId = budget_ID_label.getText();
        String projectId = projectID_combox.getValue();
        String currency = currency_txt.getText();
        String totalAmountString = totalAmount_txt.getText();
        String allocateAmountString = allocateAmount_txt.getText();
        String spendAmountString = spendAmount_txt.getText();

        if (projectId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Project ID !").show();
            return;
        }

        currency_txt.setStyle(currency_txt.getStyle()+";-fx-border-color: GREEN;");
        totalAmount_txt.setStyle(totalAmount_txt.getStyle()+";-fx-border-color: GREEN;");
        allocateAmount_txt.setStyle(allocateAmount_txt.getStyle()+";-fx-border-color: GREEN;");
        spendAmount_txt.setStyle(spendAmount_txt.getStyle()+";-fx-border-color: GREEN;");

        String currencyPattern = "^[A-Za-z ]+$";
        String amountPattern = "^(0|[1-9]\\d{0,2}(,\\d{3})*)(\\.\\d{2})?$";

        boolean isValidCurrency = currency.matches(currencyPattern);
        boolean isValidTotal = totalAmountString.matches(amountPattern);
        boolean isValidAllocate = allocateAmountString.matches(amountPattern);
        boolean isValidSpend = spendAmountString.matches(amountPattern);

        if (!isValidCurrency){
            System.out.println(currency_txt.getStyle());
            currency_txt.setStyle(currency_txt.getStyle()+";-fx-border-color: red;");
            System.out.println("Invalid Currency");
        }

        if (!isValidTotal){
            System.out.println(totalAmount_txt.getStyle());
            totalAmount_txt.setStyle(totalAmount_txt.getStyle()+";-fx-border-color: red;");
            System.out.println("Invalid Amount");
        }

       if (!isValidAllocate){
            System.out.println(allocateAmount_txt.getStyle());
            allocateAmount_txt.setStyle(allocateAmount_txt.getStyle()+";-fx-border-color: red;");
            System.out.println("Invalid Amount");
        }

       if (!isValidSpend){
            System.out.println(spendAmount_txt.getStyle());
            spendAmount_txt.setStyle(spendAmount_txt.getStyle()+";-fx-border-color: red;");
            System.out.println("Invalid Amount");
        }

       double totalAmount = Double.parseDouble(totalAmountString);
       double allocateAmount = Double.parseDouble(allocateAmountString);
       double spendAmount = Double.parseDouble(spendAmountString);

       if (isValidCurrency && isValidTotal && isValidAllocate && isValidSpend){
           BudgetDTO budgetDTO = new BudgetDTO(
                   budgetId,
                   projectId,
                   currency,
                   totalAmount,
                   allocateAmount,
                   spendAmount
           );
           boolean isSaved = budgetDao.save(budgetDTO);
           if (isSaved){
               refreshPage();
               new Alert(Alert.AlertType.INFORMATION, "Budget Saved").show();
           } else{
               new Alert(Alert.AlertType.ERROR,"Fail to saved Budget !").show();
           }
       }
    }

    @FXML
    void update_btn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String budgetId = budget_ID_label.getText();
        String projectId = projectID_combox.getValue();
        String currency = currency_txt.getText();
        String totalAmountString = totalAmount_txt.getText();
        String allocateAmountString = allocateAmount_txt.getText();
        String spendAmountString = spendAmount_txt.getText();

        double totalAmount = Double.parseDouble(totalAmountString);
        double allocateAmount = Double.parseDouble(allocateAmountString);
        double spendAmount = Double.parseDouble(spendAmountString);

        BudgetDTO budgetDTO = new BudgetDTO(
                budgetId,
                projectId,
                currency,
                totalAmount,
                allocateAmount,
                spendAmount
        );

        boolean isUpdate = budgetDao.update(budgetDTO);
        if (isUpdate){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Budget update !").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update budget !").show();
        }
    }

    public void OnClickTable(MouseEvent mouseEvent) {
        BudgetTM budgetTM = budget_tbl.getSelectionModel().getSelectedItem();
        if (budgetTM != null) {
            budget_ID_label.setText(budgetTM.getBudgetId());
            projectID_combox.setValue(budgetTM.getProjectId());
            currency_txt.setText(budgetTM.getCurrency());
            totalAmount_txt.setText(String.valueOf(budgetTM.getTotalAmount()));
            allocateAmount_txt.setText(String.valueOf(budgetTM.getAllocateAmount()));
            spendAmount_txt.setText(String.valueOf(budgetTM.getSpendAmount()));

            save_btn.setDisable(true);

            delete_btn.setDisable(false);
            update_btn.setDisable(false);
        }
    }

    public void projectID_combox_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedProjectId = projectID_combox.getSelectionModel().getSelectedItem();
        ProjectDTO projectDTO = projectDao.findById(selectedProjectId);

        if (projectDTO != null) {
            project_label_lbl.setText(projectDTO.getName());
        }
    }
}
