package model;

import model.enums.Status;
import model.enums.Visibility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
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

    // This method is to instance a Project when getting information from database
    public Project(UUID ID, Admin admin, Leader leader, String name, String creationDate, String deadline, Status status, Visibility visibility) {
        this.ID = ID;
        this.admin = admin;
        this.leader = leader;
        this.team = new HashMap<>();
        this.tasks = new LinkedList<>();
        this.name = name;
        this.creationDate = creationDate;
        this.deadline = deadline;
        this.status = status;
        this.visibility = visibility;
    }

    public Project(Admin admin, Leader leader, String name, String deadline) {
        this.ID = UUID.randomUUID();
        this.admin = admin;
        this.leader = leader;
        this.team = new HashMap<>();
        this.tasks = new LinkedList<>();
        this.name = name;
        this.creationDate = LocalDate.now().toString();
        this.deadline = deadline;
        this.status = Status.PENDING;
        this.visibility = Visibility.VISIBLE;
    }

    /**
     * Creates a new project using as a base a JSONObject.
     * @param projectJSON is the JSONObject used as starting point.
     * */
    public Project(JSONObject projectJSON) {
        try {
            this.ID = UUID.fromString(projectJSON.getString("ID"));

            this.admin = new Admin(projectJSON.getJSONObject("admin"));
            this.leader = new Leader(projectJSON.getJSONObject("leader"));

            this.team = new HashMap<>();

            JSONArray teamJSON = projectJSON.getJSONArray("team");
            for (int i = 0; i < teamJSON.length(); i++) {
                JSONObject memberJSON = teamJSON.getJSONObject(i);
                UUID memberID = UUID.fromString(memberJSON.getString("ID"));
                TeamMember member = new TeamMember(memberJSON.getJSONObject("member"));

                team.put(memberID, member);
            }

            this.tasks = new LinkedList<>();

            JSONArray tasksJSON = projectJSON.getJSONArray("tasks");
            for (int i = 0; i < tasksJSON.length(); i++) {
                JSONObject taskJSON = tasksJSON.getJSONObject(i);
                tasks.add(new Task(taskJSON));
            }

            this.name = projectJSON.getString("name");
            this.creationDate = projectJSON.getString("creationDate");
            this.deadline = projectJSON.getString("deadline");
            this.status = Status.valueOf(projectJSON.getString("status"));
            this.visibility = Visibility.valueOf(projectJSON.getString("visibility"));
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
     * */
    public HashSet<UUID> getTeamIDs() {
        return new HashSet<>(team.keySet());
    }


    /**
     * Addes a task to the list of project's tasks.
     * @param task is the object that want to add.
     * @return a boolean value depending on if the task could be added or not.
     * */
    public boolean addTask(Task task) {
        if (!tasks.contains(task))
            return tasks.add(task);
        return false;
    }

    /**
     * Removes a task from the list of project's tasks.
     * @param task is the object that want to remove.
     * @return a boolean value depending on if the task could be removed or not.
     * */
    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    /**
     * Removes a task from the list of project's tasks given its ID.
     * @param ID is the ID of the task that want to remove.
     * @return a boolean value depending on if the task could be removed or not.
     * */
    public boolean removeTask(UUID ID) {
        Task toDelete = searchTaskByID(ID);

        if (toDelete != null)
            return tasks.remove(toDelete);

        return false;
    }

    /**
     * Checks if a task exists in the list of project's tasks.
     * @param task is the object that want to check its existence.
     * @return a boolean value depending on if the task exists or not.
     * */
    public boolean checkTask(Task task) {
        return tasks.contains(task);
    }

    /**
     * Checks if a task exists in the list of project's tasks given its ID.
     * @param ID is the ID of the task that want to check its existence.
     * @return a boolean value depending on if the task exists or not.
     * */
    public boolean checkTask(UUID ID) {
        return searchTaskByID(ID) != null;
    }

    /**
     * Searches a task in the list of project's tasks.
     * @param ID is the ID of the task that want to search.
     * @return a Task object if the ID corresponds with a task or null otherwise.
     * */
    private Task searchTaskByID(UUID ID) {
        for (Task task : tasks)
            if (task.getID().equals(ID))
                return task;
        return null;
    }

    /**
     * Addes a member to the project's team.
     * @param member is the object that want to add.
     * @return a boolean value depending on if the member could be added or not.
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
     * Checks if a member exists in the project's team.
     * @param member is the object that want to check its existence.
     * @return a boolean value depending on if the member exists or not.
     * */
    public boolean checkMemberInTeam(TeamMember member) {
        return team.containsKey(member.getID());
    }

    /**
     * Checks if a task exists in the project's team given its ID.
     * @param ID is the ID of the member that want to check its existence.
     * @return a boolean value depending on if the member exists or not.
     * */
    public boolean checkMemberInTeam(UUID ID) {
        return team.containsKey(ID);
    }

    /**
     * Searches a member in the project's team.
     * @param ID is the ID of the member that want to search.
     * @return a Task object if the ID corresponds with a member or null otherwise.
     * */
    public TeamMember searchMemberByID(UUID ID) {
        for (UUID memberID : team.keySet())
            if (memberID.equals(ID))
                return team.get(memberID);
        return null;
    }

    /**
     * Removes a member from the project's member.
     * @param member is the object that want to remove.
     * @return a boolean value depending on if the task could be removed or not.
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
     * Removes a member from the project's team given its ID.
     * @param ID is the ID of the member that want to remove.
     * @return a boolean value depending on if the member could be removed or not.
     * */
    public boolean removeMember(UUID ID) {
        TeamMember toDelete = searchMemberByID(ID);

        if (toDelete != null) {
            team.remove(toDelete.getID());
            return true;
        }

        return false;
    }

    /**
     * Delays the deadline pontificated when instantiated the project.
     * @param newDeadline is the deadline that replace the old one.
     * */
    public void delayDeadline(String newDeadline) {
        setDeadline(newDeadline);
    }

    /**
     * Serializes the class Project.
     * @return a JSONObject representation of the class.
     * */
    public JSONObject serialize() {
        JSONObject projectJSON = null;

        try {
            projectJSON = new JSONObject();
            JSONArray teamJSON = new JSONArray();
            JSONArray tasksJSON = new JSONArray();

            projectJSON.put("ID", ID.toString());
            projectJSON.put("admin", admin.serialize());
            projectJSON.put("leader", leader.serialize());

            JSONObject memberJSON = new JSONObject();
            for (Map.Entry<UUID, TeamMember> entry : team.entrySet()) {
                memberJSON.put("ID", entry.getKey().toString());
                memberJSON.put("member", entry.getValue().serialize());

                teamJSON.put(memberJSON);
            }

            projectJSON.put("team", teamJSON);

            for (Task t : tasks)
                tasksJSON.put(t.serialize());

            projectJSON.put("tasks", tasksJSON);
            projectJSON.put("name", name);
            projectJSON.put("creationDate", creationDate);
            projectJSON.put("deadline", deadline);
            projectJSON.put("status", status.toString());
            projectJSON.put("visibility", visibility.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return projectJSON;
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
                ID.toString().substring(0, 8),
                admin.getName(),
                leader.getName(),
                getTeamIDs(),
                getTasksIDs(),
                name,
                creationDate,
                deadline,
                status,
                visibility
        );
    }
}
