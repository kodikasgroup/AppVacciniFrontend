package com.kodikasgroup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.kodikasgroup.utils.RequestMaker.initializeRequestMaker;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
//        scene = new Scene(loadFXML("login"));
        scene = new Scene(loadFXML("insertNewVaccinationCampaign"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(String fxml, double width, double height) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void newWindow(String fxml) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Error");
        stage.setScene(new Scene(loadFXML(fxml)));
        stage.show();
    }

    public static void newWindow(String fxml, int width, int height) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Error");
        stage.setScene(new Scene(loadFXML(fxml), width, height));
        stage.show();
    }

    public static void main(String[] args) {
        initializeRequestMaker();
        launch();
    }

}