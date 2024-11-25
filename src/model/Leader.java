package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class Leader extends User {
    private HashSet<UUID> ongoingProjects;
    private HashSet<TeamMember> dependants;

    public Leader(String name, String email, String password) {
        super(name, email, password);
        this.ongoingProjects = new HashSet<>();
        this.dependants = new HashSet<>();
    }

    /**
     * Construye un objeto de tipo MiembroEquipo del equipo a partir de un objeto de tipo JSONObject.
     * @param leaderJSON es el objeto en formato JSON que representa a la clase MiembroEquipo.
     * @author Enzo.
     * */
    public Leader(JSONObject leaderJSON) {
        super(leaderJSON);

        try {
            this.ongoingProjects = new HashSet<>();

            for (Object projectIDJSON : leaderJSON.getJSONArray("ongoingProjects"))
                ongoingProjects.add(UUID.fromString(projectIDJSON.toString()));

            this.dependants = new HashSet<>();

            JSONArray dependantsJSON = leaderJSON.getJSONArray("dependants");
            for (int i = 0; i < dependantsJSON.length(); i++) {
                JSONObject dependantJSON = dependantsJSON.getJSONObject(i);
                dependants.add(new TeamMember(dependantJSON));
            }
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

    public HashSet<TeamMember> getDependants() {
        return dependants;
    }

    public void setDependants(HashSet<TeamMember> dependants) {
        this.dependants = dependants;
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
     *
     * */
    public boolean addDependant(TeamMember member) {
        if (!dependants.contains(member)) {
            dependants.add(member);
            return true;
        }
        return false;
    }

    /**
     *
     * */
    public boolean removeDependant(TeamMember member) {
        return dependants.remove(member);
    }

    /**
     *
     * */
    public HashSet<UUID> getDependantsIDs() {
        HashSet<UUID> dependantsIDs = new HashSet<>();

        for (TeamMember member : dependants)
            dependantsIDs.add(member.getID());

        return dependantsIDs;
    }

    /**
     * Serializa la clase lider.
     * @return un objeto de tipo JSONObject con los atributos de la clase.
     * @author Ailen.
     * */
    @Override
    public JSONObject serialize() {
        JSONObject leaderJSON = null;

        try {
            leaderJSON = super.serialize();
            JSONArray ongoingProjectsJSON = new JSONArray();
            JSONArray dependantsJSON = new JSONArray();

            for (UUID projectID : ongoingProjects){
                ongoingProjectsJSON.put(projectID.toString());
            }

            leaderJSON.put("ongoingProjects", ongoingProjectsJSON);

            for(TeamMember dependant : dependants){
                dependantsJSON.put(dependant.serialize());
            }

            leaderJSON.put("dependants", dependantsJSON);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return leaderJSON;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", ongoingProjects=" + ongoingProjects +
                ", dependants=" + dependants +
                '}';
    }
}
