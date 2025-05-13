package model;

import model.enums.Status;
import model.enums.Visibility;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Task {
    private UUID ID;
    private UUID projectID;
    private String title;
    private String description;
    private TeamMember responsible;
    private String creationDate;
    private String deadline;
    private Status status;
    private Visibility visibility;

    public Task() {
    }

    public Task(UUID ID, UUID projectID, String title, String description, TeamMember responsible, String creationDate, String deadline, Status status, Visibility visibility) {
        this.ID = ID;
        this.projectID = projectID;
        this.title = title;
        this.description = description;
        this.responsible = responsible;
        this.creationDate = creationDate;
        this.deadline = deadline;
        this.status = status;
        this.visibility = visibility;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public UUID getProjectID() {
        return projectID;
    }

    public void setProjectID(UUID projectID) {
        this.projectID = projectID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TeamMember getResponsible() {
        return responsible;
    }

    public void setResponsible(TeamMember responsible) {
        this.responsible = responsible;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    /**
     * Delays the deadline pontificated when instantiated the task.
     * @param newDeadline is the deadline that replace the old one.
     * */
    public void delayDeadline(String newDeadline) {
        setDeadline(newDeadline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(ID, task.ID) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(responsible, task.responsible) && Objects.equals(creationDate, task.creationDate) && Objects.equals(deadline, task.deadline) && status == task.status && visibility == task.visibility;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, title, description, responsible, creationDate, deadline, status, visibility);
    }

    @Override
    public String toString() {
        return String.format(
                "Task\n" +
                        "  ID: %s,\n" +
                        "  Project: %s,\n" +
                        "  Title: '%s',\n" +
                        "  Description: '%s',\n" +
                        "  Responsible: %s,\n" +
                        "  Creation Date: '%s',\n" +
                        "  Deadline: '%s',\n" +
                        "  Status: %s,\n" +
                        "  Visibility: %s\n"
                ,
                ID,
                projectID,
                title,
                description,
                responsible.getName(),
                creationDate,
                deadline,
                status,
                visibility
        );
    }
}
