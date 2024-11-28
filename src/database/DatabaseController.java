package database;

import model.Admin;
import model.Leader;
import model.Project;
import model.TeamMember;
import model.enums.Role;
import model.enums.Status;
import model.enums.Visibility;

import java.sql.*;
import java.util.HashSet;
import java.util.UUID;

public class DatabaseController {
    static final String URL = "jdbc:mysql://localhost:3306/PM_Java";
    static final String USERNAME = "root";
    static final String PASSWORD = "password123A$";

    /**
     * Gets Admin information from 'PM_Java' database.
     * @param ID is the ID of the Admin searched.
     * @return an Admin object if the ID matches with a database record or null otherwise.
     * */
    public static Admin getAdmin(UUID ID) {
        String query = "SELECT admin_id, name, password, email, visibility " +
                "FROM Admin ad " +
                "JOIN Visibility vi " +
                "ON ad.visibility_id = vi.visibility_id " +
                "WHERE admin_id = '" + ID + "';";

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery(query);

            response.next();
            UUID admin_id = UUID.fromString(response.getString(1));
            String name = response.getString(2);
            String password = response.getString(3);
            String email = response.getString(4);
            Visibility visibility = Visibility.valueOf(response.getString(5));
            connection.close();

            return new Admin(admin_id, name, email, password, visibility);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Gets Leader information from 'PM_Java' database.
     * @param ID is the ID of the Leader searched.
     * @return a Leader object if the ID matches with a database record or null otherwise.
     * */
    public static Leader getLeader(UUID ID) {
        String query = "SELECT leader_id, name, password, email, visibility " +
                "FROM Leader le " +
                "JOIN Visibility vi " +
                "ON le.visibility_id = vi.visibility_id " +
                "WHERE leader_id = '" + ID + "';";

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery(query);

            response.next();
            UUID leader_id = UUID.fromString(response.getString(1));
            String name = response.getString(2);
            String password = response.getString(3);
            String email = response.getString(4);
            Visibility visibility = Visibility.valueOf(response.getString(5));
            connection.close();

            return new Leader(leader_id, name, email, password, visibility);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Gets TeamMember information from 'PM_Java' database.
     * @param ID is the ID of the TeamMember searched.
     * @return a TeamMember object if the ID matches with a database record or null otherwise.
     * */
    public static TeamMember getTeamMember(UUID ID) {
        String query = "SELECT team_member_id, name, password, email, role, visibility " +
                "FROM TeamMember tm " +
                "JOIN Visibility vi " +
                "ON tm.visibility_id = vi.visibility_id " +
                "JOIN Role ro " +
                "ON tm.role_id = ro.role_id " +
                "WHERE team_member_id = '" + ID + "';";

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery(query);

            response.next();
            UUID team_member_id = UUID.fromString(response.getString(1));
            String name = response.getString(2);
            String password = response.getString(3);
            String email = response.getString(4);
            Role role = Role.valueOf(response.getString(5));
            Visibility visibility = Visibility.valueOf(response.getString(6));
            connection.close();

            return new TeamMember(team_member_id, name, email, password, visibility, role);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets Project information from 'PM_Java' database.
     * @param ID is the ID of the Project searched.
     * @return a Project object if the ID matches with a database record or null otherwise.
     * */
    public static Project getProject(UUID ID) {
        String query = "SELECT project_id, admin_id, leader_id, name, creation_date, deadline, status, visibility " +
                "FROM Project pr " +
                "JOIN Status st " +
                "ON pr.status_id = st.status_id " +
                "JOIN Visibility vi " +
                "ON pr.visibility_id = vi.visibility_id " +
                "WHERE project_id = '" + ID + "';";

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery(query);

            response.next();
            UUID project_id = UUID.fromString(response.getString(1));
            UUID admin_id = UUID.fromString(response.getString(2));
            UUID leader_id = UUID.fromString(response.getString(3));
            String name = response.getString(4);
            String creation_date = response.getString(5);
            String deadline = response.getString(6);
            Status status = Status.valueOf(response.getString(7));
            Visibility visibility = Visibility.valueOf(response.getString(8));
            connection.close();

            Admin admin = getAdmin(admin_id);
            Leader leader = getLeader(leader_id);

            return new Project(project_id, admin, leader, name, creation_date, deadline, status, visibility);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets dependants information from 'PM_Java' database.
     * @param ID is the ID of an Admin or Leader.
     * @return a collection of dependants' IDs or null if the ID belongs to a TeamMember or doesn't match with any record.
     * */
    public static HashSet<UUID> getDependants(UUID ID) {
        HashSet<UUID> IDs = new HashSet<>();
        String query = null;

        Admin admin = getAdmin(ID);

        if (admin != null) {
            // The ID belongs to an Admin
            query = "SELECT * FROM Admin_Leader WHERE admin_id = '" + ID + "';";
        } else {
            Leader leader = getLeader(ID);

            if (leader != null) {
                // The ID belongs to a Leader
                query = "SELECT * FROM Leader_TeamMember WHERE leader_id = '" + ID + "';";
            } else
                return null; // At this point, the ID belongs to a TeamMember (with no dependants) or doesn't match with any record
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery(query);

            while (response.next())
                IDs.add(UUID.fromString(response.getString(2)));
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return IDs;
    }
}
