package model;

import model.enums.Role;
import model.enums.Visibility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class TeamMember extends User {
    private HashSet<UUID> ongoingProjects;
    private Role role;

    public TeamMember() { super(); }

    public TeamMember(UUID ID, String name, String email, String password, Visibility visibility, HashSet<UUID> ongoingProjects, Role role) {
        super(ID, name, email, password, visibility);
        this.ongoingProjects = ongoingProjects;
        this.role = role;
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
