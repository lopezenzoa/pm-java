package model.managers;

import model.User;

import java.util.HashSet;
import java.util.UUID;

public class DependantsManager<T extends User> {
    private HashSet<T> dependants;

    public DependantsManager() {
        this.dependants = new HashSet<>();
    }

    /**
     * Adds a new dependant to the dependants' list of the user.
     * @param dependant is the new dependant.
     * @return a boolean value depending on if the dependant could be added or not.
     * */
    public boolean addDependant(T dependant) {
        if (!dependants.contains(dependant))
            return dependants.add(dependant);
        return false;
    }

    /**
     * Removes a dependant from the dependants' list of the user.
     * @param dependant is the dependant that want to delete.
     * @return a boolean value depending on if the dependant could be removed or not.
     * */
    public boolean removeDependant(T dependant) {
        return dependants.remove(dependant);
    }

    /**
     * Removes a dependant from the dependants' list of the user given its ID.
     * @param ID is the dependant that want to delete.
     * @return a boolean value depending on if the dependant could be removed or not.
     * */
    public boolean removeDependant(UUID ID) {
        T toDelete = searchDependantByID(ID);

        if (toDelete != null)
            return dependants.remove(toDelete);
        else
            return false;
    }

    /**
     * Searches a dependant in the dependant's list of the user.
     * @param ID is the ID of the dependant that want to search.
     * @return a T object if the ID corresponds with a dependant or null otherwise.
     * */
    public T searchDependantByID(UUID ID) {
        for (T dependant : dependants)
            if (dependant.getID().equals(ID))
                return dependant;
        return null;
    }

    /**
     * Returns a collection of dependants IDs.
     * @return a HashSet made of dependants IDs.
     * */
    public HashSet<UUID> getDependantsIDs() {
        HashSet<UUID> dependantsIDs = new HashSet<>();

        for (T dependant : dependants)
            dependantsIDs.add(dependant.getID());

        return dependantsIDs;
    }
}
