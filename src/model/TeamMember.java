package model;

import model.enums.Role;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class TeamMember extends User {
    private HashSet<UUID> ongoingProjects;
    private Role role;

    public TeamMember(String name, String email, String password, Role role) {
        super(name, email, password);
        this.ongoingProjects = new HashSet<>();
        this.role = role;
    }

    /**
     * Creates a new team member using as a base a JSONObject.
     * @param teamMemberJSON is the JSONObject used as starting point.
     * */
    public TeamMember(JSONObject teamMemberJSON) {
        super(teamMemberJSON);

        try {
            this.ongoingProjects = new HashSet<>();

            for (Object projectIDJSON : teamMemberJSON.getJSONArray("ongoingProjects"))
                ongoingProjects.add(UUID.fromString(projectIDJSON.toString()));

            this.role = Role.valueOf(teamMemberJSON.getString("role"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HashSet<UUID> getOngoingProjects() {
        return ongoingProjects;
    }

    public void setOngoingProjects(HashSet<UUID> ongoingProjects) {
        this.ongoingProjects = ongoingProjects;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Adds a new project in the ongoing projects' list of the member.
     * @param projectID is the ID of the project that want to add.
     * @return a boolean value depending on if the project could be added or not.
     * */
    public boolean addOngoingProject(UUID projectID) {
        if (!ongoingProjects.contains(projectID))
            return ongoingProjects.add(projectID);
        return false;
    }

    /**
     * Removes a project from the ongoing projects' list of the member.
     * @param project is the ID of the project that want to remove.
     * @return a boolean value depending on if the project could be added or not.
     * */
    public boolean removeOngoingProject(Project project) {
        return ongoingProjects.remove(project);
    }

    /**
     * Serializes the class TeamMember.
     * @return a JSONObject representation of the class.
     * */
    @Override
    public JSONObject serialize() {
        JSONObject memberJSON = null;

        try {
            memberJSON = super.serialize();
            JSONArray ongoingProjectsJSON = new JSONArray();

            for (UUID projectID : ongoingProjects)
                ongoingProjectsJSON.put(projectID.toString());

            memberJSON.put("ongoingProjects", ongoingProjectsJSON);
            memberJSON.put("role", role.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return memberJSON;
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format(
                    "  ongoingProjects: %s,\n" +
                    "  role: %s\n"
                    ,
                    ongoingProjects,
                    role
                );
    }
}
