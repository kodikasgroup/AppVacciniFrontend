module com.kodikasgroup {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.kodikasgroup to javafx.fxml;
    exports com.kodikasgroup;
}
