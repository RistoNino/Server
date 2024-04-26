package org.uid.ristonino.server.model;

import java.sql.*;

public class DatabaseHandler {
    private final String url = "jdbc:sqlite:RistoNino.db";
    private static Connection conn;

    private static final DatabaseHandler instance = new DatabaseHandler();
    private DatabaseHandler() {}
    public static DatabaseHandler getInstance() {
        return instance;
    }

    public void openConnection() {
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection established");
            }
        }
        catch (SQLException e) {
            //logError("Error while connecting to the database: " + e.getMessage());
        }
    }

    public void closeConnection() {

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            //logError("Error while closing the connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
