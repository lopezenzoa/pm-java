package controller;

import database.DatabaseController;
import model.Admin;
import model.Leader;
import model.TeamMember;
import model.User;
import model.interfaces.CRUDable;
import view.UserView;

import java.sql.*;
import java.util.HashSet;
import java.util.UUID;

public class UserController implements CRUDable<User> {
    private User model;
    private UserView view;

    // Database Connection
    private final String URL = "jdbc:mysql://localhost:3306/PM_Java";
    private final String USERNAME = "root";
    private final String PASSWORD = "password123A$";

    public UserController(User model, UserView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void create() {
        String query = null;

        if (model instanceof Admin) {
            query = String.format(
                    "INSERT INTO Admin(admin_id, name, password, email, visibility_id) VALUES " +
                        "('%s', '%s', '%s', '%s', %d);",
                    model.getID().toString(),
                    model.getName(),
                    model.getPassword(),
                    model.getEmail(),
                    1 // VISIBLE
            );
        } else if (model instanceof Leader) {
            query = String.format(
                    "INSERT INTO Leader(leader_id, name, password, email, visibility_id) VALUES " +
                            "('%s', '%s', '%s', '%s', %d);",
                    model.getID().toString(),
                    model.getName(),
                    model.getPassword(),
                    model.getEmail(),
                    1 // VISIBLE
            );
        } else {
            query = String.format(
                    "INSERT INTO TeamMember(team_member_id, name, password, email, visibility_id, role_id) VALUES " +
                            "('%s', '%s', '%s', '%s', %d, %d);",
                    model.getID().toString(),
                    model.getName(),
                    model.getPassword(),
                    model.getEmail(),
                    1, // VISIBLE
                    5 // Data Analyst
            );
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();

            if (model instanceof Admin || model instanceof Leader)
                addDependants();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read() {
        if (model instanceof Admin)
            view.printUser(DatabaseController.getAdmin(model.getID()));
        else if (model instanceof Leader)
            view.printUser(DatabaseController.getLeader(model.getID()));
        else
            view.printUser(DatabaseController.getTeamMember(model.getID()));
    }

    @Override
    public void update(User newModel) {}

    @Override
    public void delete() {}

    /**
     * Auxiliary method to add dependants for Admins and Leaders in the intermediate tables of the 'PM_Java' database.
     * */
    public void addDependants() {
        // This collection is used to save every intermediate table insertion separated
        HashSet<String> queries = new HashSet<>();

        if (model instanceof Admin) {
            HashSet<UUID> IDs = ((Admin) model).getDependantsIDs();

            if (!IDs.isEmpty())
                for (UUID ID : IDs) {
                    String query = String.format(
                            "INSERT INTO Admin_Leader(admin_id, leader_id) VALUES " +
                                    "('%s', '%s');",
                            model.getID().toString(),
                            ID.toString()
                            );

                    queries.add(query);
                }
        } else if (model instanceof Leader) {
            HashSet<UUID> IDs = ((Leader) model).getDependantsIDs();

            if (!IDs.isEmpty())
                for (UUID ID : IDs) {
                    String query = String.format(
                            "INSERT INTO Leader_TeamMember(leader_id, team_member_id) VALUES " +
                                    "('%s', '%s');",
                            model.getID().toString(),
                            ID.toString()
                    );

                    queries.add(query);
                }
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();

            for (String query : queries)
                statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
