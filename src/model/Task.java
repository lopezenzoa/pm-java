package model;

import model.enums.Status;
import model.enums.Visibility;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Task {
    private UUID ID;
    private String title;
    private String description;
    private TeamMember responsible;
    private String creationDate;
    private String deadline;
    private Status status;
    private Visibility visibility;

    public Task(String title, String description, TeamMember responsible, String deadline) {
        this.ID = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.responsible = responsible;
        this.creationDate = LocalDate.now().toString();
        this.deadline = deadline;
        this.status = Status.PENDING;
        this.visibility = Visibility.VISIBLE;
    }

    /**
     * Creates a new task using as a base a JSONObject.
     * @param taskJSON is the JSONObject used as starting point.
     * */
    public Task(JSONObject taskJSON) {
        try {
            this.ID = UUID.fromString(taskJSON.getString("ID"));
            this.title = taskJSON.getString("title");
            this.description = taskJSON.getString("description");
            this.responsible = new TeamMember(taskJSON.getJSONObject("responsible"));
            this.creationDate = taskJSON.getString("creationDate");
            this.deadline = taskJSON.getString("deadline");
            this.status = Status.valueOf(taskJSON.getString("status"));
            this.visibility = Visibility.valueOf(taskJSON.getString("visibility"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
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

    /**
     * Serializes the class Task.
     * @return a JSONObject representation of the class.
     * */
    public JSONObject serialize() {
        JSONObject taskJSON = null;

        try {
            taskJSON = new JSONObject();

            taskJSON.put("ID", ID.toString());
            taskJSON.put("title", title);
            taskJSON.put("description", description);
            taskJSON.put("responsible", responsible.serialize());
            taskJSON.put("creationDate", creationDate);
            taskJSON.put("deadline", deadline);
            taskJSON.put("status", status.toString());
            taskJSON.put("visibility", visibility.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return taskJSON;
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
        return "Task{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", responsible=" + responsible +
                ", creationDate='" + creationDate + '\'' +
                ", deadline='" + deadline + '\'' +
                ", status=" + status +
                ", visibility=" + visibility +
                '}';
    }
}
