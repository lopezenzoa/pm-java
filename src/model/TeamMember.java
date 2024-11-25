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
     * Construye un objeto de tipo MiembroEquipo del equipo a partir de un objeto de tipo JSONObject.
     * @param teamMemberJSON es el objeto en formato JSON que representa a la clase MiembroEquipo.
     * @author Enzo.
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
     *
     * */
    public boolean addOngoingProject(UUID projectID) {
        if (!ongoingProjects.contains(projectID)) {
            ongoingProjects.add(projectID);
            return true;
        }
        return false;
    }

    /**
     *
     * */
    public boolean removeOngoingProject(Project project) {
        return ongoingProjects.remove(project);
    }

    /**
     * Serializa la clase MiembroEquipo.
     * @return un objeto de tipo JSONObject con los atributos de la clase.
     * @author Ailen.
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
                ", ongoingProjects=" + ongoingProjects +
                ", role=" + role +
                '}';
    }
}
