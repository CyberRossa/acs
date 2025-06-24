package org.example.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserData {
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty role = new SimpleStringProperty();

    public String getUsername() {
        return username.get();
    }
    public void setUsername(String username) {
        this.username.set(username);
    }
    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }
    public void setPassword(String password) {
        this.password.set(password);
    }
    public StringProperty passwordProperty() {
        return password;
    }

    public String getRole() {
        return role.get();
    }
    public void setRole(String role) {
        this.role.set(role);
    }
    public StringProperty roleProperty() {
        return role;
    }
}
