package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.model.UserData;
import org.example.services.UserService;
import org.example.services.GlobalServices;

public class UserController {


    @FXML private TableView<UserData> userTable;
    @FXML private TableColumn<UserData, String> usernameCol;
    @FXML private TableColumn<UserData, String> roleCol;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleChoice;
    @FXML private Button addUserButton;
    @FXML private Button deleteUserButton;
    @FXML private Button updateButton;

    private final UserService userService = GlobalServices.getUserService();
    private final ObservableList<UserData> users = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        usernameCol.setCellValueFactory(data -> data.getValue().usernameProperty());
        roleCol.setCellValueFactory(data -> data.getValue().roleProperty());

        roleChoice.setItems(FXCollections.observableArrayList("USER", "ADMIN"));
        roleChoice.getSelectionModel().selectFirst();

        refreshUserList();

        userTable.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) {
                usernameField.setText(sel.getUsername());
                passwordField.clear();
                roleChoice.setValue(sel.getRole());
            }
        });

        addUserButton.setOnAction(e -> addUser());
        deleteUserButton.setOnAction(e -> deleteUser());
    }

    private void refreshUserList() {
        users.setAll(userService.getAllUsers());
        userTable.setItems(users);
    }

    private void addUser() {
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank() || roleChoice.getValue() == null) {
            showAlert("Eksik bilgi", "Lütfen tüm alanları doldurun.");
            return;
        }
        boolean ok = userService.addUser(
                usernameField.getText().trim(),
                passwordField.getText(),
                roleChoice.getValue());
        if (ok) {
            refreshUserList();
            clearForm();
        } else {
            showAlert("Hata", "Kullanıcı eklenemedi.");
        }
    }

    private void updateUser() {
        UserData sel = userTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("Seçim","Lütfen bir kullanıcı seçin."); return; }
        if (passwordField.getText().isBlank()) {
            showAlert("Eksik bilgi","Yeni şifre girin."); return;
        }
        boolean ok = userService.updateUserPassword(sel.getUsername(), passwordField.getText());
        if (ok) {
            showAlert("Başarılı","Şifre güncellendi.");
            clearForm();
        } else {
            showAlert("Hata","Güncelleme başarısız.");
        }
    }


    private void deleteUser() {
        UserData sel = userTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("Seçim","Lütfen bir kullanıcı seçin."); return; }
        if (userService.deleteUser(sel.getUsername())) {
            refreshUserList();
            clearForm();
        } else {
            showAlert("Hata","Silme başarısız.");
        }
    }

    private void clearForm() {
        usernameField.clear();
        passwordField.clear();
        roleChoice.setValue(null);
        userTable.getSelectionModel().clearSelection();
    }
    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}