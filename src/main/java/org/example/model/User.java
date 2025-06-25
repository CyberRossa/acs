package org.example.model;

import java.util.Objects;
public class User {
    private String username;
    private String passwordHash;  // BCrypt ile hash’lenmiş parola
    private String role;          // e.g. "ADMIN" veya "USER"

    // Getters & setters
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String h) { this.passwordHash = h; }

    public String getRole() { return role; }
    public void setRole(String r) { this.role = r; }
}


/*public class User {
    private String username;
    private String password;
    private String role;

    public User() {}
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    @Override public boolean equals(Object o) {
        if (this == o) return true; if (!(o instanceof User)) return false;
        User u = (User) o; return Objects.equals(username,u.username)&&
                Objects.equals(password,u.password)&&
                Objects.equals(role,u.role);
    }
    @Override public int hashCode() { return Objects.hash(username,password,role); }
    @Override public String toString() {
        return "User{"+"username='"+username+'\''+",role='"+role+'\''+'}';
    }
} */