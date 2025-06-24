package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.model.UserData;
import org.example.services.GlobalServices;
import org.example.services.UserService;


public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label messageLabel;

    private final UserService userService = GlobalServices.getUserService();

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Lütfen kullanıcı adı ve şifreyi doldurun.");
            return;
        }

        UserData user = userService.getUserByUsername(username);

        if (user == null) {
            showError("Kullanıcı bulunamadı.");
            return;
        }

        if (userService.validatePassword(user, password)) {
            openMainWindow(user.getRole());
        } else {
            showError("Şifre hatalı.");
        }
    }

    private void openMainWindow(String userRole) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);

            MainController mainController = loader.getController();
            mainController.setUserRole(userRole);
            mainController.setPrimaryStage(stage);

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Sistem hatası oluştu.");
        }
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
