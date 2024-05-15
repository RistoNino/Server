package org.uid.ristonino.server.model;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.uid.ristonino.server.view.SceneHandler;

import java.sql.*;


public class DatabaseHandler {


    // lista query, da finire

    private final String inserisciCategoria = "INSERT INTO Categories (name) VALUES (?);";
    private final String inserisciPiatto = "INSERT INTO Items (name, category_id, description, price) VALUES (?, ?, ?, ?);";
    private final String inserisci = "INSERT INTO Items_Ingredients (Item_Id, Ingredient_Id) VALUES (?, ?);";
    private final String inserisciIngrediente = "INSERT INTO Ingredients (name) VALUES (?);";
    private final String checkPasswordSt = "SELECT Password FROM Users WHERE Username = ?;";
    private final String createUser = "INSERT INTO Users (Username, Password, PrivilegesLevel) VALUES (?, ?, ?);";
    private final String checkUsername = "SELECT Username FROM Users WHERE Username = ?;";
    private final String getTavoli = "SELECT * FROM Tavoli";
    private final String getOrdiniDaTavolo = "SELECT o.id, Name, Price FROM Items AS i, Ordini_Items AS oi, Ordini AS o, Tavoli AS t WHERE i.Id = oi.idItems AND oi.idOrdine = o.Id AND o.idTavolo = t.Id AND t.id = ?;";


    private final String url = "jdbc:sqlite:RistoNino.db";
    Connection con;

    // classe singleton
    private static DatabaseHandler instance = new DatabaseHandler();
    private DatabaseHandler() {}
    public static DatabaseHandler getInstance() {return instance; }

    public void openConnection()  {
        try {
            con = DriverManager.getConnection(url);
            if (con != null && !con.isClosed()) {
                Debug.getInstance().print("Connected to " + url);
            }
        } catch (SQLException e) {
            Debug.getInstance().print("Error while trying to connect to the database:  " + e.getMessage());
        }
    }

    public void closeConnection()  {
        try {
            if (con != null) {
                con.close();
            }
            if (con != null && con.isClosed()) {
                Debug.getInstance().print("Disconnecting from " + url);
            }
        } catch (SQLException e) {
            SceneHandler.getInstance().createErrorMessage("Check log");
            Debug.getInstance().print("Error while closing the connection:  " + e.getMessage());
        }
    }

    public Boolean checkUsername(String username) {
        try {
            PreparedStatement st = con.prepareStatement(checkUsername);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            return rs.next();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean createUser(String username, String password, int privilegesLevel) {
        try {
            if (!checkUsername(username)) {
                PreparedStatement st = con.prepareStatement(createUser);
                st.setString(1,username);
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                st.setString(2,hashedPassword);
                st.setInt(3,privilegesLevel);
                return st.executeUpdate() > 0;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean checkPassword(String username, String passwordInput) {
        try {
            PreparedStatement st = con.prepareStatement(checkPasswordSt);
            st.setString(1,username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("Password");
                return BCrypt.checkpw(passwordInput,hashedPassword);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Ordine getOrdineByTableId(int id) {
        try {
            PreparedStatement st = con.prepareStatement(getOrdiniDaTavolo);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            Ordine ordine = new Ordine(rs.getInt("Id"));
            while (rs.next()) {

            }
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
