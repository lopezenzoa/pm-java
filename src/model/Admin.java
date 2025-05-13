package model;

import model.enums.Visibility;

import java.util.HashSet;
import java.util.UUID;

public class Admin extends User {
    private HashSet<Leader> dependants;

    public Admin() { super(); }

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

    @Override
    public String toString() {
        return super.toString() +
                String.format(
                    "  dependants: %s\n"
                    ,
                    dependants
                );
    }
}
