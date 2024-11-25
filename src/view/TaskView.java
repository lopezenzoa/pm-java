package view;

import model.Task;
import model.TeamMember;

public class TaskView {
    public TaskView() {}

    public void printTask(Task task) {
        System.out.println(task);
    }

    public void printResponsible(TeamMember responsible) {
        System.out.println(responsible);
    }
}
