package model;

import model.enums.Status;
import model.enums.Visibility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.*;

public class Project {
    private Integer ID;
    private Admin admin;
    private Leader leader;
    private HashMap<Integer, TeamMember> team;
    private LinkedList<Task> tasks;
    private String name;
    private String creationDate;
    private String deadline;
    private Status status;
    private Visibility visibility;

    public Project() {
    }

    public Project(Integer ID, Admin admin, Leader leader, HashMap<Integer, TeamMember> team, LinkedList<Task> tasks, String name, String creationDate, String deadline, Status status, Visibility visibility) {
        this.ID = ID;
        this.admin = admin;
        this.leader = leader;
        this.team = team;
        this.tasks = tasks;
        this.name = name;
        this.creationDate = creationDate;
        this.deadline = deadline;
        this.status = status;
        this.visibility = visibility;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
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

    public HashMap<Integer, TeamMember> getTeam() {
        return team;
    }

    public void setTeam(HashMap<Integer, TeamMember> team) {
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
        return String.format(
                "Project\n" +
                        "  ID: %s,\n" +
                        "  Admin: %s,\n" +
                        "  Leader: %s,\n" +
                        "  Team: %s,\n" +
                        "  Tasks: %s,\n" +
                        "  Name: '%s',\n" +
                        "  Creation Date: '%s',\n" +
                        "  Deadline: '%s',\n" +
                        "  Status: %s,\n" +
                        "  Visibility: %s\n"
                ,
                ID,
                admin.getName(),
                leader.getName(),
                team,
                tasks,
                name,
                creationDate,
                deadline,
                status,
                visibility
        );
    }
}
