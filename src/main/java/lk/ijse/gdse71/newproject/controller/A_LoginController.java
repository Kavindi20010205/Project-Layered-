package lk.ijse.gdse71.newproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.newproject.DTO.LoginDTO;
import lk.ijse.gdse71.newproject.dao.custom.LoginDao;
import lk.ijse.gdse71.newproject.dao.custom.impl.LoginDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class A_LoginController implements Initializable {
    public Button refresh_btn;
    @FXML
    private Tooltip toolTip_userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
        }
    }

    private void refreshPage() {
        login_btn.setDisable(false);

        userName_txt.setText("");
        password_txt.setText("");
    }

    @FXML
    private Button login_btn;

    @FXML
    private AnchorPane login_panel;

    @FXML
    private Label password_lbl;

    @FXML
    private TextField password_txt;

    @FXML
    private Label userName_lbl;

    @FXML
    private TextField userName_txt;

    public static String employeeID;

//    LoginModel loginModel = new LoginModel();
//    LoginDaoImpl loginDao = new LoginDaoImpl();
    LoginDao loginDao = new LoginDaoImpl();

    public void login_btn_OnAction(ActionEvent event) throws IOException, SQLException {
       loginTo();
        //Password nathuwa cheack karanna

     //   cheakUserNameAndPassword();
    }

    private void cheakUserNameAndPassword() throws SQLException, IOException {
        String userNameGiven = userName_txt.getText();
        String passwordGiven = password_txt.getText();

        if (userNameGiven == null|| userNameGiven.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter UserName !").show();
            return;
        }
        if (passwordGiven == null|| passwordGiven.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Password !").show();
            return;
        }
        userName_txt.setStyle(userName_txt.getStyle()+";-fx-border-color: GREEN;");
        password_txt.setStyle(password_txt.getStyle()+";-fx-border-color: GREEN;");

        String userNamePattern = "EM\\d{3}";
        String passwordPattern = "\\d{2}@[a-z]{2}";


        boolean isValidUserName = userNameGiven.matches(userNamePattern);
        boolean isValidPassword = passwordGiven.matches(passwordPattern);

        if (!isValidUserName){
            userName_txt.setStyle(userName_txt.getStyle()+";-fx-border-color: red;");
            toolTip_userName.setText("invalid User Name");
            refreshPage();
            return;
        }
        if (!isValidPassword){
            password_txt.setStyle(password_txt.getStyle()+";-fx-border-color: red;");
        }
        checkLogin();
    }

    private void checkLogin() throws SQLException, IOException {
        String userNameGiven = userName_txt.getText();
        String passwordGiven = password_txt.getText();

        String correctPassword;

        LoginDTO loginDTO = loginDao.findById(userNameGiven);

        if (loginDTO != null) {
            correctPassword = loginDTO.getPassword();
            if (correctPassword.equals(passwordGiven)) {
                employeeID= loginDTO.getUsername();
                loginTo();
            } else{
                new Alert(Alert.AlertType.ERROR, "Incorrect Password !").show();
                password_txt.setText("");
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "User Name not founded !").show();
            refreshPage();
            return;
        }
    }

    private void loginTo() throws IOException {
        login_panel.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/B_DashBoarddFx.fxml"));
        login_panel.getChildren().add(load);
    }

    public void refresh_btn_OnAction(ActionEvent event) {
        refreshPage();
    }

}
