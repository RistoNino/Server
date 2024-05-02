package org.uid.ristonino.server.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseHandler {


    // lista query, da finire
    private final String inseriscipiatto = "INSERT INTO users VALUES(?, ?, ?, ?);";

    private final String url = "jdbc:sqlite:RistoNino.db";
    Connection con;

    // classe singleton
    private static DatabaseHandler instance = new DatabaseHandler();
    private DatabaseHandler() {}
    public static DatabaseHandler getInstance() {return instance; }

    public void openConnection()  {
        try {
            con = DriverManager.getConnection(url);
            if (con != null && !con.isClosed() && Debug.IS_ACTIVE) {
                System.out.println("Connecting to " + url);
            }
        } catch (SQLException e) {
            System.out.println("Error while trying to connect to the database:  " + e.getMessage());
        }
    }

    public void closeConnection()  {
        try {
            if (con != null) {
                con.close();
            }
            if (con != null && con.isClosed() && Debug.IS_ACTIVE) {
                System.out.println("Disconnecting from " + url);
            }
        } catch (SQLException e) {
            System.out.println("error while closing the connection:  " + e.getMessage());
        }
    }
}
