package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class Admin extends User {
    private HashSet<Leader> dependants;

    public Admin(String name, String email, String password) {
        super(name, email, password);
        this.dependants = new HashSet<>();
    }

    /**
     * Creates a new admin using as a base a JSONObject.
     * @param adminJSON is the JSONObject used as starting point.
     * */
    public Admin(JSONObject adminJSON) {
        super(adminJSON);

        try {
            this.dependants = new HashSet<>();

            JSONArray dependantsJSON = adminJSON.getJSONArray("dependants");
            for (int i = 0; i < dependantsJSON.length(); i++) {
                JSONObject dependantJSON = dependantsJSON.getJSONObject(i);
                dependants.add(new Leader(dependantJSON));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    /**
     * Serializes the class Admin.
     * @return a JSONObject representation of the class.
     * */
    @Override
    public JSONObject serialize(){
        JSONObject adminJSON = null;

        try {
            adminJSON = super.serialize();
            JSONArray dependantsJSON = new JSONArray();

            for(Leader dependant : dependants){
                dependantsJSON.put(dependant.serialize());
            }

            adminJSON.put("dependants", dependantsJSON);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return adminJSON;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", dependants=" + dependants +
                '}';
    }
}
