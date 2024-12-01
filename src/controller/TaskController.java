package controller;

import database.DatabaseController;
import model.Task;
import model.enums.Status;
import model.enums.Visibility;
import model.interfaces.CRUDable;
import view.TaskView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TaskController implements CRUDable<Task> {
    public Task model;
    public TaskView view;

    // Database Connection
    private final String URL = "jdbc:mysql://localhost:3306/PM_Java";
    private final String USERNAME = "root";
    private final String PASSWORD = "password123A$";

    public TaskController(Task model, TaskView view) {
        this.model = model;
        this.view = view;
    }

    public Task getModel() {
        return model;
    }

    public void setModel(Task model) {
        this.model = model;
    }

    @Override
    public void create() {
        String query = String.format(
                "INSERT INTO Task(task_id, project_id, title, description, responsible_id, creation_date, deadline, status_id, visibility_id) VALUES " +
                    "('%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, %d)",
                model.getID().toString(),
                model.getProjectID(),
                model.getTitle(),
                model.getDescription(),
                model.getResponsible().getID().toString(),
                model.getCreationDate(),
                model.getDeadline(),
                1, // PENDING
                1 // VISIBLE
        );

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
        view.printTask(DatabaseController.getTask(model.getID()));
    }

    @Override
    public void update(Task newModel) {
        newModel.setID(model.getID());
        newModel.setProjectID(model.getProjectID());
        int visibility_id = 1;
        int status_id = 1;

        if (newModel.getVisibility().equals(Visibility.INVISIBLE))
            visibility_id = 2;

        if (newModel.getStatus().equals(Status.FINISHED))
            status_id = 2;

        String query = String.format(
                "UPDATE Task SET title = '%s', decription = '%s', responsible_id = '%s', deadline = '%s', status_id = %d, visibility_id = %d " +
                        "WHERE task_id = '%s';",
                newModel.getTitle(),
                newModel.getDescription(),
                newModel.getResponsible().getID(),
                newModel.getDeadline(),
                status_id,
                visibility_id,
                newModel.getID()
                );

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
}
