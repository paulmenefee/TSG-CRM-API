package com.tsg.restfulservice.model;

import java.time.LocalDate;

public class Project {
    private String projectId;
    private String projectName;
    private int clientId;
    private String Summary;
    private LocalDate dueDate;
    private boolean isActive;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Project CheckProjectID(Project project) {
        String projectId = project.getProjectId();
        String updatedString = projectId.replaceAll("[ /]", "-");
        project.setProjectId(updatedString);
        return project;
    }
}
