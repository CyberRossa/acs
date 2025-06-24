package org.example.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class ReportRecord {
    private final StringProperty reportId;
    private final StringProperty projectName;
    private final StringProperty clientName;
    private ACSPLUSInput input;
    private ACSPLUSOutput output;
    private final ObjectProperty<LocalDateTime> createdDate;

    public ReportRecord(String reportId, String projectName, String clientName,
                        ACSPLUSInput input, ACSPLUSOutput output, LocalDateTime createdDate) {
        this.reportId = new SimpleStringProperty(reportId);
        this.projectName = new SimpleStringProperty(projectName);
        this.clientName = new SimpleStringProperty(clientName);
        this.input = input;
        this.output = output;
        this.createdDate = new SimpleObjectProperty<>(createdDate);
    }

    public String getReportId() { return reportId.get(); }
    public StringProperty reportIdProperty() { return reportId; }

    public String getProjectName() { return projectName.get(); }
    public StringProperty projectNameProperty() { return projectName; }

    public String getClientName() { return clientName.get(); }
    public StringProperty clientNameProperty() { return clientName; }

    public ACSPLUSInput getInput() { return input; }
    public void setInput(ACSPLUSInput input) { this.input = input; }

    public ACSPLUSOutput getOutput() { return output; }
    public void setOutput(ACSPLUSOutput output) { this.output = output; }

    public LocalDateTime getCreatedDate() { return createdDate.get(); }
    public ObjectProperty<LocalDateTime> createdDateProperty() { return createdDate; }
}