package org.example.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogEntry {
    private final StringProperty date = new SimpleStringProperty();
    private final StringProperty level = new SimpleStringProperty();
    private final StringProperty message = new SimpleStringProperty();

    public LogEntry(String date, String level, String message) {
        this.date.set(date);
        this.level.set(level);
        this.message.set(message);
    }

    public String getDate() { return date.get(); }
    public StringProperty dateProperty() { return date; }

    public String getLevel() { return level.get(); }
    public StringProperty levelProperty() { return level; }

    public String getMessage() { return message.get(); }
    public StringProperty messageProperty() { return message; }
}
