package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static MySQLConnection instance;
    private Connection connection;

    private final String DB_NAME = "PM_Java";
    private final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private final String USERNAME = "root";
    private final String PASSWORD = "root";

    private MySQLConnection() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MySQLConnection getInstance() {
        if (instance == null)
            return new MySQLConnection();
        else
            return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
