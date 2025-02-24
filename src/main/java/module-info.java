module lk.ijse.gdse71.newproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
  //  requires lombok;

    opens lk.ijse.gdse71.newproject.DTO.tm to javafx.base;
    opens lk.ijse.gdse71.newproject.controller to javafx.fxml;
    exports lk.ijse.gdse71.newproject;
}