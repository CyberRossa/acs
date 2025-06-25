package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.loader.StaticDataLoader;
import org.example.services.DriverManagerDatabaseService;
import org.example.services.DatabaseService;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        var db = new DriverManagerDatabaseService("jdbc:h2:mem:acsdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "sa", "");
        new StaticDataLoader(db).loadAll();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Admin Panel - Login");
        stage.show();
    }

   /* public static void changeScene(String fxmlFile) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/" + fxmlFile));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Main.class.getResource("/style/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }*/



    public static void main(String[] args) throws SQLException {

        DatabaseService dbService = new DriverManagerDatabaseService(
                "jdbc:h2:mem:acsdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
                "sa",
                "" );

        // 2) Statik verileri yükle
        StaticDataLoader loader = new StaticDataLoader(dbService);
        loader.loadAll();
        launch(args);
    }
}




