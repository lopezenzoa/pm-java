package database;

import controller.UserController;
import model.*;
import model.enums.Role;
import model.enums.Status;
import model.enums.Visibility;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

public class DatabaseController {
    private Connection connection;

    public DatabaseController() {
        this.connection = MySQLConnection.getInstance().getConnection();
    }


    /**
     * Gets Admin information from 'PM_Java' database.
     * @param ID is the ID of the Admin searched.
     * @return an Admin object if the ID matches with a database record or null otherwise.
     * */
    public Admin getAdmin(UUID ID) {
        String query = "SELECT admin_id, name, password, email, visibility " +
                "FROM Admin ad " +
                "JOIN Visibility vi " +
                "ON ad.visibility_id = vi.visibility_id " +
                "WHERE admin_id = '" + ID + "';";

        try (Statement stmt = connection.createStatement()){
            ResultSet response = stmt.executeQuery(query);

            response.next();

            UUID admin_id = UUID.fromString(response.getString(1));
            String name = response.getString(2);
            String password = response.getString(3);
            String email = response.getString(4);
            Visibility visibility = Visibility.valueOf(response.getString(5));

            Admin admin = new Admin(admin_id, name, email, password, visibility);

            // Getting the dependants
            query = "SELECT leader_id FROM Admin_Leader WHERE admin_id = '" + ID + "';";

            response = stmt.executeQuery(query);

            while (response.next())
                admin.addDependant(getLeader(UUID.fromString(response.getString(1))));

            connection.close();

            return admin;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Gets Leader information from 'PM_Java' database.
     * @param ID is the ID of the Leader searched.
     * @return a Leader object if the ID matches with a database record or null otherwise.
     * */
    public Leader getLeader(UUID ID) {
        String query = "SELECT leader_id, name, password, email, visibility " +
                "FROM Leader le " +
                "JOIN Visibility vi " +
                "ON le.visibility_id = vi.visibility_id " +
                "WHERE leader_id = '" + ID + "';";

        try(Statement stmt = connection.createStatement()) {
            ResultSet response = stmt.executeQuery(query);

            response.next();

            UUID leader_id = UUID.fromString(response.getString(1));
            String name = response.getString(2);
            String password = response.getString(3);
            String email = response.getString(4);
            Visibility visibility = Visibility.valueOf(response.getString(5));

            Leader leader = new Leader(leader_id, name, email, password, visibility);

            query = "SELECT team_member_id FROM Leader_TeamMember WHERE leader_id = '" + ID + "';";

            response = stmt.executeQuery(query);

            while (response.next())
                leader.addDependant(getTeamMember(UUID.fromString(response.getString(1))));

            connection.close();

            return leader;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Gets TeamMember information from 'PM_Java' database.
     * @param ID is the ID of the TeamMember searched.
     * @return a TeamMember object if the ID matches with a database record or null otherwise.
     * */
    public TeamMember getTeamMember(UUID ID) {
        String query = "SELECT team_member_id, name, password, email, role, visibility " +
                "FROM TeamMember tm " +
                "JOIN Visibility vi " +
                "ON tm.visibility_id = vi.visibility_id " +
                "JOIN Role ro " +
                "ON tm.role_id = ro.role_id " +
                "WHERE team_member_id = '" + ID + "';";

        try(Statement statement = connection.createStatement();) {
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
    public Project getProject(UUID ID) {
        String query = "SELECT project_id, admin_id, leader_id, name, creation_date, deadline, status, visibility " +
                "FROM Project pr " +
                "JOIN Status st " +
                "ON pr.status_id = st.status_id " +
                "JOIN Visibility vi " +
                "ON pr.visibility_id = vi.visibility_id " +
                "WHERE project_id = '" + ID + "';";

        try(Statement statement = connection.createStatement();) {
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

            Project project = new Project(project_id, admin, leader, name, creation_date, deadline, status, visibility);

            ArrayList<Task> tasks = getTasks();
            if (!tasks.isEmpty())
                for (Task task : tasks)
                    project.addTask(task);

            HashMap<UUID, TeamMember> team = getProjectTeam(project_id);

            if (!team.isEmpty())
                for (TeamMember member : team.values())
                    project.addTeamMember(member);

            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a collection with all the Admins reached from 'PM_Java' database.
     * @return an ArrayList of type Admin with all records at the database.
     * */
    public ArrayList<Admin> getAdmins() {
        String query = "SELECT admin_id FROM Admin ORDER BY name";
        ArrayList<Admin> admins = new ArrayList<>();

        try(Statement statement = connection.createStatement();) {
            ResultSet response = statement.executeQuery(query);

            while (response.next())
                admins.add(getAdmin(UUID.fromString(response.getString(1))));
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return admins;
    }

    /**
     * Returns a collection with all the Admins reached from 'PM_Java' database.
     * @return an ArrayList of type Admin with all records at the database.
     * */
    public ArrayList<Leader> getLeaders() {
        String query = "SELECT leader_id FROM Leader ORDER BY name";
        ArrayList<Leader> leaders = new ArrayList<>();

        try(Statement statement = connection.createStatement();) {
            ResultSet response = statement.executeQuery(query);

            while (response.next())
                leaders.add(getLeader(UUID.fromString(response.getString(1))));
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return leaders;
    }

    /**
     * Returns a collection with all the Admins reached from 'PM_Java' database.
     * @return an ArrayList of type Admin with all records at the database.
     * */
    public ArrayList<TeamMember> getTeamMembers() {
        String query = "SELECT team_member_id FROM TeamMember ORDER BY name";
        ArrayList<TeamMember> teamMembers = new ArrayList<>();

        try(Statement statement = connection.createStatement();) {
            ResultSet response = statement.executeQuery(query);

            while (response.next())
                teamMembers.add(getTeamMember(UUID.fromString(response.getString(1))));
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return teamMembers;
    }

    /**
     * Gets Task information from 'PM_Java' database.
     * @param ID is the ID of the Task searched.
     * @return a Task object if the ID matches with a database record or null otherwise.
     * */
    public Task getTask(UUID ID) {
        String query = "SELECT task_id, project_id, title, description, responsible_id, creation_date, deadline, status, visibility " +
                "FROM Task ta " +
                "JOIN Status st " +
                "ON ta.status_id = st.status_id " +
                "JOIN Visibility vi " +
                "ON ta.visibility_id = vi.visibility_id " +
                "WHERE task_id = '" + ID + "';";

        try(Statement statement = connection.createStatement();) {
            ResultSet response = statement.executeQuery(query);

            response.next();

            UUID task_id = UUID.fromString(response.getString(1));
            UUID project_id = UUID.fromString(response.getString(2));
            String title = response.getString(3);
            String description = response.getString(4);
            UUID responsible_id = UUID.fromString(response.getString(5));
            String creation_date = response.getString(6);
            String deadline = response.getString(7);
            Status status = Status.valueOf(response.getString(8));
            Visibility visibility = Visibility.valueOf(response.getString(9));

            // Getting responsible from 'PM_Java' database
            TeamMember responsible = getTeamMember(responsible_id);

            return new Task(task_id, project_id, title, description, responsible, creation_date, deadline, status, visibility);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a collection with all the Projects reached from 'PM_Java' database.
     * @return an ArrayList of type Project with all records at the database.
     * */
    public ArrayList<Project> getProjects() {
        String query = "SELECT project_id FROM Project ORDER BY name;";
        ArrayList<Project> projects = new ArrayList<>();

        try(Statement statement = connection.createStatement();) {
            ResultSet response = statement.executeQuery(query);

            while (response.next())
                projects.add(getProject(UUID.fromString(response.getString(1))));
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return projects;
    }

    /**
     * Returns a collection with all the Tasks reached from 'PM_Java' database.
     * @return an ArrayList of type Task with all records at the database.
     * */
    public ArrayList<Task> getTasks() {
        String query = "SELECT task_id FROM Task;";
        ArrayList<Task> tasks = new ArrayList<>();

        try(Statement statement = connection.createStatement();) {
            ResultSet response = statement.executeQuery(query);

            while (response.next())
                tasks.add(getTask(UUID.fromString(response.getString(1))));
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }

    /**
     * Returns a map with all the TeamMembers with their IDs reached from 'PM_Java' database.
     * @return an HashMap of type UUID, TeamMember with all records at the database.
     * */
    public HashMap<UUID, TeamMember> getProjectTeam(UUID projectID) {
        String query = "SELECT team_member_id FROM Team WHERE project_id = '" + projectID + "';";
        HashMap<UUID, TeamMember> team = new HashMap<>();

        try(Statement statement = connection.createStatement()) {
            ResultSet response = statement.executeQuery(query);

            while (response.next()) {
                TeamMember member = getTeamMember(UUID.fromString(response.getString(1)));
                team.put(member.getID(), member);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return team;
    }
}
