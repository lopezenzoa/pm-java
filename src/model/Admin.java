package model;

import model.enums.Visibility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class Admin extends User {
    private HashSet<Leader> dependants;

    public Admin() {
        super();
    }

    public Admin(UUID ID, String name, String email, String password, Visibility visibility, HashSet<Leader> dependants) {
        super(ID, name, email, password, visibility);
        this.dependants = dependants;
    }

    public HashSet<Leader> getDependants() {
        return dependants;
    }

    public void setDependants(HashSet<Leader> dependants) {
        this.dependants = dependants;
    }

    /**
     * Adds a new dependant to the dependants' list of the admin.
     * @param leader is the new dependant.
     * @return a boolean value depending on if the dependant could be added or not.
     * */
    public boolean addDependant(Leader leader) {
        if (!dependants.contains(leader))
            return dependants.add(leader);
        return false;
    }

    /**
     * Removes a dependant from the dependants' list of the admin.
     * @param leader is the dependant that want to delete.
     * @return a boolean value depending on if the dependant could be removed or not.
     * */
    public boolean removeDependant(Leader leader) {
        return dependants.remove(leader);
    }

    /**
     * Removes a dependant from the dependants' list of the admin given its ID.
     * @param ID is the dependant that want to delete.
     * @return a boolean value depending on if the dependant could be removed or not.
     * */
    public boolean removeDependant(UUID ID) {
        Leader toDelete = searchDependantByID(ID);

        if (toDelete != null)
            return dependants.remove(toDelete);
        else
            return false;
    }

    /**
     * Searches a dependant in the dependant's list of the admin.
     * @param ID is the ID of the dependant that want to search.
     * @return a TeamMember object if the ID corresponds with a dependant or null otherwise.
     * */
    public Leader searchDependantByID(UUID ID) {
        for (Leader leader : dependants)
            if (leader.getID().equals(ID))
                return leader;
        return null;
    }

    /**
     * Returns a collection of dependants IDs.
     * @return a HashSet made of dependants IDs.
     * */
    public HashSet<UUID> getDependantsIDs() {
        HashSet<UUID> dependantsIDs = new HashSet<>();

        for (Leader dependant : dependants)
            dependantsIDs.add(dependant.getID());

        return dependantsIDs;
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format(
                    "  dependants: %s\n"
                    ,
                    getDependantsIDs()
                );
    }
}
