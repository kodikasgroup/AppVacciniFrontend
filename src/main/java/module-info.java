module com.kodikasgroup {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.kodikasgroup to javafx.fxml;
    exports com.kodikasgroup;
    exports com.kodikasgroup.controller;
    opens com.kodikasgroup.controller to javafx.fxml;
}
