package lk.ijse.gdse71.newproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/A_LoginFx.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Project Management System");

        Image image = new Image(getClass().getResourceAsStream("/image/SDC_logo.png"));
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {launch();}
}
