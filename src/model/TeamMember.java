package model;

import model.enums.Role;
import model.enums.Visibility;

import java.util.HashSet;
import java.util.UUID;

public class TeamMember extends User {
    private HashSet<UUID> ongoingProjects;
    private Role role;

    public TeamMember() { super(); }

    public TeamMember(Integer ID, String name, String email, String password, Visibility visibility, HashSet<UUID> ongoingProjects, Role role) {
        super(ID, name, email, password, visibility);
        this.ongoingProjects = ongoingProjects;
        this.role = role;
    }

    public HashSet<Integer> getOngoingProjects() {
        return ongoingProjects;
    }

    public void setOngoingProjects(HashSet<Integer> ongoingProjects) {
        this.ongoingProjects = ongoingProjects;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format(
                    "  ongoingProjects: %s,\n" +
                    "  role: %s\n"
                    ,
                    ongoingProjects,
                    role
                );
    }
}
