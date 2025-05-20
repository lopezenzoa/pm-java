package model;

import model.enums.Visibility;

import java.util.HashSet;
import java.util.UUID;

public class Leader extends User {
    private HashSet<Integer> ongoingProjects;
    private HashSet<TeamMember> dependants;

    public Leader() { super(); }

    public Leader(Integer ID, String name, String email, String password, Visibility visibility, HashSet<Integer> ongoingProjects, HashSet<TeamMember> dependants) {
        super(ID, name, email, password, visibility);
        this.ongoingProjects = ongoingProjects;
        this.dependants = dependants;
    }

    public HashSet<Integer> getOngoingProjects() {
        return ongoingProjects;
    }

    public void setOngoingProjects(HashSet<Integer> ongoingProjects) {
        this.ongoingProjects = ongoingProjects;
    }

    public HashSet<TeamMember> getDependants() {
        return dependants;
    }

    public void setDependants(HashSet<TeamMember> dependants) {
        this.dependants = dependants;
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format(
                        "  ongoingProjects: %s,\n" +
                        "  dependants: %s\n"
                        ,
                        ongoingProjects,
                        dependants
                );
    }
}
