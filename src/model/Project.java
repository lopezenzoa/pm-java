package model;

import model.enums.Status;
import model.enums.Visibility;

import java.util.*;

public class Project {
    private UUID ID;
    private Admin admin;
    private Leader leader;
    private HashMap<UUID, TeamMember> team;
    private LinkedList<Task> tasks;
    private String name;
    private String creationDate;
    private String deadline;
    private Status status;
    private Visibility visibility;

    public Project(Admin admin, Leader leader, String name, String deadline) {
        this.ID = UUID.randomUUID();
        this.admin = admin;
        this.leader = leader;
        this.team = new HashMap<>();
        this.tasks = new LinkedList<>();
        this.name = name;
        this.creationDate = "2024-23-11";
        this.deadline = deadline;
        this.status = Status.PENDING;
        this.visibility = Visibility.VISIBLE;
    }

    /*
    public Project(JSONObject projectJSON) {
        try {
            this.ID = jsonObject.getInt("id");
            this.administrador = new Administrador(jsonObject.getJSONObject("administrador"));
            this.lider = new Lider(jsonObject.getJSONObject("lider"));
            this.altaObaja = AltaBaja.valueOf(jsonObject.getString("altaObaja"));
            this.equipo = new HashSet<>();
            JSONArray equipoJSON = jsonObject.getJSONArray("equipo");

            for (int i = 0; i < equipoJSON.length(); i++) {
                JSONObject miembroJSON = equipoJSON.getJSONObject(i);
                equipo.add(new MiembroEquipo(miembroJSON));
            }

            this.tareas = new LinkedList<>();
            JSONArray tareasJSON = jsonObject.getJSONArray("tareas");

            for (int i = 0; i < tareasJSON.length(); i++) {
                JSONObject tareaJSON = tareasJSON.getJSONObject(i);
                tareas.add(new Tarea(tareaJSON));
            }

            this.nombre = jsonObject.getString("nombre");

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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public HashMap<UUID, TeamMember> getTeam() {
        return team;
    }

    public void setTeam(HashMap<UUID, TeamMember> team) {
        this.team = team;
    }

    public LinkedList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(LinkedList<Task> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
     * Returns a collection of tasks IDs.
     * @return a HashSet made of tasks IDs.
     * @author Enzo.
     * */
    public HashSet<UUID> getTasksIDs() {
        HashSet<UUID> taskIDs = new HashSet<>();

        for (Task task : tasks)
            taskIDs.add(task.getID());

        return taskIDs;
    }

    /**
     * Returns a collection of team members IDs.
     * @return a HashSet made of team IDs.
     * @author Enzo.
     * */
    public HashSet<UUID> getTeamIDs() {
        return new HashSet<>(team.keySet());
    }


    /**
     *
     * */
    public boolean addTask(Task task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
            return true;
        }
        return false;
    }

    /**
     *
     * */
    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    /**
     *
     * */
    public boolean removeTaskByID(UUID ID) {
        Task toDelete = searchTaskByID(ID);
        if (toDelete != null) {
            tasks.remove(toDelete);
            return true;
        }
        return false;
    }

    /**
     *
     * */
    public boolean checkTask(Task task) {
        return tasks.contains(task);
    }

    /**
     *
     * */
    public boolean checkTaskByID(UUID ID) {
        return searchTaskByID(ID) != null;
    }

    /**
     *
     * */
    private Task searchTaskByID(UUID ID) {
        for (Task task : tasks)
            if (task.getID().equals(ID))
                return task;
        return null;
    }

    /**
     *
     * */
    public boolean addTeamMember(TeamMember member) {
        if (!team.containsKey(member.getID())) {
            team.put(member.getID(), member);
            return true;
        }
        else
            return false;
    }

    /**
     *
     * */
    public boolean checkMemberInTeam(TeamMember member) {
        return team.containsKey(member.getID());
    }

    /**
     *
     * */
    public boolean removeMember(TeamMember member) {
        if (team.containsKey(member.getID())) {
            team.remove(member.getID());
            return true;
        }
        else
            return false;
    }

    /**
     *
     * */
    public void delayDeadline(String newDeadline) {
        setDeadline(newDeadline);
    }

    /**
     * Serializes the class Project.
     * @return a JSONObject representation of the class.
     * @author Enzo.
     * */
    /*
    public JSONObject serializar() {
        JSONObject proyectoJSON = null;

        try {
            proyectoJSON = new JSONObject();
            JSONArray equipoJSON = new JSONArray();
            JSONArray tareasJSON = new JSONArray();

            proyectoJSON.put("id", id);
            proyectoJSON.put("administrador", administrador.serializar());
            proyectoJSON.put("lider", lider.serializar());
            proyectoJSON.put("altaObaja", altaObaja.toString());
            for (MiembroEquipo miembro : equipo)
                equipoJSON.put(miembro.serializar());

            proyectoJSON.put("equipo", equipoJSON);

            for (Tarea t : tareas)
                tareasJSON.put(t.serializar());

            proyectoJSON.put("tareas", tareasJSON);
            proyectoJSON.put("nombre", nombre);
            proyectoJSON.put("estado", estado.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return proyectoJSON;
    }

     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(ID, project.ID) && Objects.equals(admin, project.admin) && Objects.equals(leader, project.leader) && Objects.equals(team, project.team) && Objects.equals(tasks, project.tasks) && Objects.equals(name, project.name) && Objects.equals(creationDate, project.creationDate) && Objects.equals(deadline, project.deadline) && status == project.status && visibility == project.visibility;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, admin, leader, team, tasks, name, creationDate, deadline, status, visibility);
    }

    @Override
    public String toString() {
        return "Project{" +
                "ID=" + ID +
                ", admin=" + admin +
                ", leader=" + leader +
                ", team=" + team +
                ", tasks=" + tasks +
                ", name='" + name + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", deadline='" + deadline + '\'' +
                ", status=" + status +
                ", visibility=" + visibility +
                '}';
    }
}
