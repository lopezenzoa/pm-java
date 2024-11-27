package model;

import model.enums.Visibility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class Leader extends User {
    private HashSet<UUID> ongoingProjects;
    private HashSet<TeamMember> dependants;

    public Leader(UUID ID, String name, String email, String password, Visibility visibility) {
        super(ID, name, email, password, visibility);
        this.ongoingProjects = new HashSet<>();
        this.dependants = new HashSet<>();
    }

    public Leader(String name, String email, String password) {
        super(name, email, password);
        this.ongoingProjects = new HashSet<>();
        this.dependants = new HashSet<>();
    }

    /**
     * Creates a new leader using as a base a JSONObject.
     * @param leaderJSON is the JSONObject used as starting point.
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
     * Adds a new project in the ongoing projects' list of the leader.
     * @param projectID is the ID of the project that want to add.
     * @return a boolean value depending on if the project could be added or not.
     * */
    public boolean addOngoingProject(UUID projectID) {
        if (!ongoingProjects.contains(projectID))
            return ongoingProjects.add(projectID);
        return false;
    }

    /**
     * Removes a project from the ongoing projects' list of the leader.
     * @param project is the ID of the project that want to remove.
     * @return a boolean value depending on if the project could be added or not.
     * */
    public boolean removeOngoingProject(Project project) {
        return ongoingProjects.remove(project);
    }

    /**
     * Adds a new dependant to the dependants' list of the leader.
     * @param member is the new dependant.
     * @return a boolean value depending on if the dependant could be added or not.
     * */
    public boolean addDependant(TeamMember member) {
        if (!dependants.contains(member))
            return dependants.add(member);
        return false;
    }

    /**
     * Removes a dependant from the dependants' list of the leader.
     * @param member is the dependant that want to delete.
     * @return a boolean value depending on if the dependant could be removed or not.
     * */
    public boolean removeDependant(TeamMember member) {
        return dependants.remove(member);
    }

    /**
     * Removes a dependant from the dependants' list of the leader given its ID.
     * @param ID is the dependant that want to delete.
     * @return a boolean value depending on if the dependant could be removed or not.
     * */
    public boolean removeDependant(UUID ID) {
        TeamMember toDelete = searchDependantByID(ID);

        if (toDelete != null)
            return dependants.remove(toDelete);
        else
            return false;
    }

    /**
     * Searches a dependant in the dependant's list of the leader.
     * @param ID is the ID of the dependant that want to search.
     * @return a TeamMember object if the ID corresponds with a dependant or null otherwise.
     * */
    public TeamMember searchDependantByID(UUID ID) {
        for (TeamMember member : dependants)
            if (member.getID().equals(ID))
                return member;
        return null;
    }

    /**
     * Returns a collection of dependants IDs.
     * @return a HashSet made of dependants IDs.
     * */
    public HashSet<UUID> getDependantsIDs() {
        HashSet<UUID> dependantsIDs = new HashSet<>();

        for (TeamMember member : dependants)
            dependantsIDs.add(member.getID());

        return dependantsIDs;
    }

    /**
     * Serializes the class Leader.
     * @return a JSONObject representation of the class.
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
                String.format(
                        "  ongoingProjects: %s,\n" +
                        "  dependants: %s\n"
                        ,
                        ongoingProjects,
                        getDependantsIDs()
                );
    }
}
