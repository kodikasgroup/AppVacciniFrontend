module com.kodikasgroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires java.logging;
    requires java.sql;
    requires static lombok;

    opens com.kodikasgroup to javafx.fxml;
    exports com.kodikasgroup;
    exports com.kodikasgroup.controller;
    opens com.kodikasgroup.controller to javafx.fxml;
}
