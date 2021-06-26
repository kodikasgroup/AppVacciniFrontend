module com.kodikasgroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.logging;
    requires java.sql;
    requires static lombok;

    opens com.kodikasgroup to javafx.fxml;
    exports com.kodikasgroup;
    exports com.kodikasgroup.controller;
    exports com.kodikasgroup.model;
    exports com.kodikasgroup.wrapper;
    exports com.kodikasgroup.id;
    opens com.kodikasgroup.controller to javafx.fxml;

}