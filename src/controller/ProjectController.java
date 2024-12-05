package controller;

import database.DatabaseController;
import model.*;
import model.enums.Status;
import model.enums.Visibility;
import model.interfaces.CRUDable;
import view.ProjectView;
import view.TaskView;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class ProjectController implements CRUDable<Project> {
    private Project model;
    private ProjectView view;

    // Database Connection
    private final String URL = "jdbc:mysql://localhost:3306/PM_Java";
    private final String USERNAME = "root";
    private final String PASSWORD = "password123A$";

    public ProjectController(Project model, ProjectView view) {
        this.model = model;
        this.view = view;
    }

    public Project getModel() {
        return model;
    }

    public void setModel(Project model) {
        this.model = model;
    }

    @Override
    public void create() {
        String query = String.format(
                "INSERT INTO Project(project_id, admin_id, leader_id, name, creation_date, deadline, status_id, visibility_id) VALUES " +
                        "('%s', '%s', '%s', '%s', '%s', '%s', %d, %d);",
                model.getID().toString(),
                model.getAdmin().getID().toString(),
                model.getLeader().getID().toString(),
                model.getName(),
                model.getCreationDate(),
                model.getDeadline(),
                1, // PENDING
                1 // VISIBLE
        );

        for (Task task : model.getTasks())
            createTask(task);

        createTeam();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read() {
        view.printProject(DatabaseController.getProject(model.getID()));
    }

    @Override
    public void update(Project newModel) {
        newModel.setID(model.getID());
        int visibility_id = 1;
        int status_id = 1;

        if (newModel.getVisibility().equals(Visibility.INVISIBLE))
            visibility_id = 2;

        if (newModel.getStatus().equals(Status.FINISHED))
            status_id = 2;

        String query = String.format(
                "UPDATE Project SET admin_id = '%s', leader_id = '%s', name = '%s', deadline = '%s', status_id = %d, visibility_id = %d " +
                        "WHERE project_id = '%s';",
                newModel.getAdmin().getID(),
                newModel.getLeader().getID(),
                newModel.getName(),
                newModel.getDeadline(),
                status_id,
                visibility_id,
                newModel.getID()
        );

        for (Task task : newModel.getTasks())
            createTask(task);

        createTeam();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setModel(newModel);
    }

    @Override
    public void delete() {
        model.setVisibility(Visibility.INVISIBLE);
        update(model);
    }

    /**
     * Creates a task into 'PM_Java' database only if the task didn't exist before.
     * @param task is the object ot type Task which is going to be created.
     * */
    public void createTask(Task task) {
        ArrayList<Task> tasks = DatabaseController.getTasks();

        if (!tasks.contains(task)) {
            TaskController taskController = new TaskController(task, new TaskView());
            taskController.create();
        }
    }

    /**
     * Creates an initial team into 'PM_Java' database for the intermediate table 'Team'.
     * */
    public void createTeam() {
        StringBuilder query = new StringBuilder("INSERT INTO Team(project_id, team_member_id) VALUES ");

        Iterator<UUID> iterator = model.getTeamIDs().iterator();

        while (iterator.hasNext()) {
            UUID currentID = iterator.next();

            // This conditional may be redundant, but is crucial to set the query correctly
            if (!iterator.hasNext())
                query.append(String.format("('%s', '%s');", model.getID(), currentID));
            else
                query.append(String.format("('%s', '%s'), ", model.getID(), currentID));
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.valueOf(query));
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
