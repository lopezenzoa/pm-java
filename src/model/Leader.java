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

    public Leader() { super(); }

    public Leader(UUID ID, String name, String email, String password, Visibility visibility, HashSet<UUID> ongoingProjects, HashSet<TeamMember> dependants) {
        super(ID, name, email, password, visibility);
        this.ongoingProjects = ongoingProjects;
        this.dependants = dependants;
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
