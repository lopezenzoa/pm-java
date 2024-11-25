package view;

import model.Admin;
import model.Leader;
import model.TeamMember;
import model.User;

import java.util.UUID;

public class UserView {
    public UserView() {}

    public void printUser(User user) {
        System.out.println(user);
    }

    public void printDependants(Leader leader) {
        System.out.println("Dependants");
        for (TeamMember dependant : leader.getDependants())
            System.out.println("  " + dependant.getName() + "\n");
    }

    public void printDependants(Admin admin) {
        System.out.println("Dependants");
        for (Leader dependant : admin.getDependants())
            System.out.println("  " + dependant.getName() + "\n");
    }

    public void printOngoingProjects(Leader leader) {
        System.out.println("On-Going Projects");
        for (UUID projectID: leader.getOngoingProjects())
            System.out.println("  " + projectID + "\n");
    }

    public void printOngoingProjects(TeamMember member) {
        System.out.println("On-Going Projects");
        for (UUID projectID: member.getOngoingProjects())
            System.out.println("  " + projectID + "\n");
    }
}
