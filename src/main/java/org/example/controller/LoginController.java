package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.services.DriverManagerDatabaseService;
import org.mindrot.jbcrypt.BCrypt;
import org.example.controller.MainController;


public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private UserDAO userDao;

    @FXML
    public void initialize() {
        // DB servisi
        var db = new DriverManagerDatabaseService(
                "jdbc:h2:mem:acsdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL", "sa", "");
        userDao = new UserDAO(db);
    }

    @FXML
    private void onLogin() {
        String u = usernameField.getText().trim();
        String p = passwordField.getText();

        userDao.findByUsername(u).ifPresentOrElse(user -> {
            if (BCrypt.checkpw(p, user.getPasswordHash())) {
                // Başarılı: ana ekrana geçiş
                try {
                    // Main.fxml’inizi yükleyip sahneyi değiştirin:
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
                    Pane root = loader.load();
// Burada tip MainController
                    org.example.controller.MainController mainCtrl = loader.getController();
                    mainCtrl.setUserRole(user.getRole());
                    // Yeni sahne:
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.getScene().setRoot(root);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    errorLabel.setText("Ekran yüklenemedi");
                }
            } else {
                errorLabel.setText("Parola hatalı");
            }
        }, () -> {
            errorLabel.setText("Kullanıcı bulunamadı");
        });
    }
}

