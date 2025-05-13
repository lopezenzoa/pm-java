package model;

import model.enums.Visibility;

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
