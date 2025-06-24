package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.services.GlobalServices;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        GlobalServices.initialize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Admin Panel - Login");
        stage.show();
    }

    public static void changeScene(String fxmlFile) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/" + fxmlFile));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Main.class.getResource("/style/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }



    public static void main(String[] args) {
        launch(args);
    }
}




