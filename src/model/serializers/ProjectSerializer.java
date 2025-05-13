package model.serializers;

import model.*;
import model.enums.Status;
import model.enums.Visibility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class ProjectSerializer implements Serializable<Project> {
    AdminSerializer adminSerializer;
    LeaderSerializer leaderSerializer;
    TeamMemberSerializer teamMemberSerializer;
    TaskSerializer taskSerializer;

    public ProjectSerializer(UserSerializer userSerializer) {
        this.adminSerializer = new AdminSerializer();
        this.leaderSerializer = new LeaderSerializer();
        this.teamMemberSerializer = new TeamMemberSerializer();
        this.taskSerializer = new TaskSerializer();
    }

    @Override
    /**
     * Creates a new project using as a base a JSONObject.
     * @param projectJSON is the JSONObject used as starting point.
     * */
    public Project deserialize(JSONObject projectJSON) {
        Project project = new Project();
        HashMap<UUID, TeamMember> team = new HashMap<>();
        LinkedList<Task> tasks = new LinkedList<>();

        try {
            project.setID(UUID.fromString(projectJSON.getString("ID")));
            project.setAdmin(adminSerializer.deserialize(projectJSON.getJSONObject("admin")));
            project.setLeader(leaderSerializer.deserialize(projectJSON.getJSONObject("leader")));

            JSONArray teamJSON = projectJSON.getJSONArray("team");
            for (int i = 0; i < teamJSON.length(); i++) {
                JSONObject memberJSON = teamJSON.getJSONObject(i);
                UUID memberID = UUID.fromString(memberJSON.getString("ID"));
                TeamMember member = teamMemberSerializer.deserialize(memberJSON.getJSONObject("member"));

                team.put(memberID, member);
            }

            JSONArray tasksJSON = projectJSON.getJSONArray("tasks");
            for (int i = 0; i < tasksJSON.length(); i++) {
                JSONObject taskJSON = tasksJSON.getJSONObject(i);
                tasks.add(taskSerializer.deserialize(taskJSON));
            }

            project.setName(projectJSON.getString("name"));
            project.setCreationDate(projectJSON.getString("creationDate"));
            project.setDeadline(projectJSON.getString("deadline"));
            project.setStatus(Status.valueOf(projectJSON.getString("status")));
            project.setVisibility(Visibility.valueOf(projectJSON.getString("visibility")));

            return project;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    /**
     * Serializes the class Project.
     * @return a JSONObject representation of the class.
     * */
    public JSONObject serialize(Project project) {
        JSONObject projectJSON = null;

        try {
            projectJSON = new JSONObject();
            JSONArray teamJSON = new JSONArray();
            JSONArray tasksJSON = new JSONArray();

            projectJSON.put("ID", project.getID());
            projectJSON.put("admin", adminSerializer.serialize(project.getAdmin()));
            projectJSON.put("leader", leaderSerializer.serialize(project.getLeader()));

            JSONObject memberJSON = new JSONObject();
            for (Map.Entry<UUID, TeamMember> entry : project.getTeam().entrySet()) {
                memberJSON.put("ID", entry.getKey().toString());
                memberJSON.put("member", teamMemberSerializer.serialize(entry.getValue()));

                teamJSON.put(memberJSON);
            }

            projectJSON.put("team", teamJSON);

            for (Task t : project.getTasks())
                tasksJSON.put(taskSerializer.serialize(t));

            projectJSON.put("tasks", tasksJSON);
            projectJSON.put("name", project.getName());
            projectJSON.put("creationDate", project.getCreationDate());
            projectJSON.put("deadline", project.getDeadline());
            projectJSON.put("status", project.getStatus());
            projectJSON.put("visibility", project.getVisibility());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return projectJSON;
    }
}
