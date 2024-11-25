package model;

import model.enums.Status;
import model.enums.Visibility;

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
        this.status = Status.PENDING;
        this.visibility = Visibility.VISIBLE;
    }

    /**
     * Construye una tarea a partir de un objeto de tipo JSONObject.
     * @param jsonObject es el objeto en formato JSON que representa a la tarea.
     * @author Enzo.
     * */
    /*
    public Tarea(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.titulo = jsonObject.getString("titulo");
            this.descripcion = jsonObject.getString("descripcion");
            this.responsable = new MiembroEquipo(jsonObject.getJSONObject("responsable"));
            this.altaObaja = AltaBaja.valueOf(jsonObject.getString("altaObaja"));
            String estadoJSON = jsonObject.getString("estado");
            this.estado = Estado.valueOf(estadoJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

     */

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
     *
     * */
    public void delayDeadline(String newDeadline) {
        setDeadline(newDeadline);
    }

    /**
     * Serializa la clase Tarea.
     * @return un objeto de tipo JSONObject con los atributos de la tarea.
     * @author Enzo.
     * */
    /*
    public JSONObject serializar() {
        JSONObject tareaJSON = null;

        try {
            tareaJSON = new JSONObject();

            tareaJSON.put("id", id);
            tareaJSON.put("titulo", titulo);
            tareaJSON.put("descripcion", descripcion);
            tareaJSON.put("responsable", responsable.serializar());
            tareaJSON.put("estado", estado.toString());
            tareaJSON.put("altaObaja", altaObaja.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tareaJSON;
    }

     */

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
