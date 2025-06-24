package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button calculationButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button priceButton;

    @FXML
    private Button userButton;

    @FXML
    private Button logoutButton;

    @FXML
    private StackPane contentArea;

    private String userRole = "USER"; // default, login sonrası setlenecek

    private Stage primaryStage;

    public void setUserRole(String role) {
        this.userRole = role;
        setupMenu();
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setFullScreen(true);
    }

    @FXML
    public void initialize() {
        setupMenu();

        calculationButton.setOnAction(e -> loadPage("Calculation.fxml"));
        reportButton.setOnAction(e -> loadPage("Report.fxml"));
        priceButton.setOnAction(e -> loadPage("Price.fxml"));
        userButton.setOnAction(e -> loadPage("User.fxml"));
        logoutButton.setOnAction(e -> logout());
    }

    private void setupMenu() {
        boolean isUser = "USER".equalsIgnoreCase(userRole);
        priceButton.setDisable(isUser);
        userButton.setDisable(isUser);
    }

    private void loadPage(String fxmlFile) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("/view/" + fxmlFile));
            contentArea.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
            // Burada kullanıcıya anlamlı hata mesajı verebilirsin.
        }
    }

    private void logout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Scene scene = contentArea.getScene();
            if (scene != null) {
                scene.setRoot(root);
            } else if (primaryStage != null) {
                primaryStage.setScene(new Scene(root));
            }
            if (primaryStage != null) {
                primaryStage.setFullScreen(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Hata mesajı ekleyebilirsin.
        }
    }
}
