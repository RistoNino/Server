//TODO: Sistemare i warning

package org.uid.ristonino.server.model;


import javafx.util.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.uid.ristonino.server.model.services.OrderService;
import org.uid.ristonino.server.model.services.TableService;
import org.uid.ristonino.server.model.types.Flag;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.model.types.Ordine;
import org.uid.ristonino.server.model.types.Table;
import org.uid.ristonino.server.view.SceneHandler;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseHandler {


    // lista query, da finire

    private final String inserisciCategoria = "INSERT INTO Categories (name) VALUES (?);";
    private final String inserisciPiatto = "INSERT INTO Items (name, category_id, description, price) VALUES (?, ?, ?, ?);";
    private final String inserisci = "INSERT INTO Items_Ingredients (Item_Id, Ingredient_Id) VALUES (?, ?);";
    private final String inserisciIngrediente = "INSERT INTO Ingredients (name) VALUES (?);";
    private final String checkPasswordSt = "SELECT Password FROM Users WHERE Username = ?;";
    private final String createUser = "INSERT INTO Users (Username, Password, PrivilegesLevel) VALUES (?, ?, ?);";
    private final String checkUsername = "SELECT Username FROM Users WHERE Username = ?;";
    private final String getTavoli = "SELECT * FROM Tables";
    private final String getCategories = "SELECT * FROM Categories";
    private final String getItems = "SELECT * FROM Items";
    private final String getOrdineDaTavolo = "SELECT o.Id AS OrderId, i.id AS ItemId, Name, oi.Quantity, Price, oi.Note AS ItemNote FROM Items AS i, Orders_Items AS oi, Orders AS o, Tables AS t WHERE i.Id = oi.idItem AND oi.idOrder = o.Id AND o.idTable = t.Id;";
    private final String getIngredientsByItemId = "SELECT Name FROM Ingredients INNER JOIN Items_Ingredients on Ingredients.Id = Items_Ingredients.Ingredient_Id WHERE Items_Ingredients.Item_Id = ?;";
    private final String getFlags = "SELECT * FROM Flags;";
    //private final String createOrder = "INSERT INTO Orders (IdTable) VALUES (?);";
    private final String putItemsInOrder = "INSERT INTO Orders_Items (IdOrder, IdItem, Quantity, Note) VALUES (?,?,?,?)";

    private final String getOrdini="SELECT o.Id AS OrderId, i.Id AS ItemId, i.Name, oi.Quantity, i.Price, oi.Note AS ItemNote, t.Id AS TableId FROM Items AS i JOIN Orders_Items AS oi ON i.Id = oi.IdItem JOIN Orders AS o ON oi.IdOrder = o.Id JOIN Tables AS t ON o.IdTable = t.Id;";
    private final String createOrder = "INSERT INTO Orders (IdTable, Note) VALUES (?,?);";
    private final String getOrdiniPagati = "SELECT COUNT(*) FROM Orders WHERE Pagato = 1;";
    private final String getOrdiniNonPagati = "SELECT COUNT(*) FROM Orders WHERE Pagato = 0;";


    OrderService allOrders;
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

    public void loadOrders() {
        try {
            allOrders=OrderService.getInstance();
            PreparedStatement st = con.prepareStatement(getOrdineDaTavolo);
            ResultSet rs = st.executeQuery();
            Ordine ordine;
            while (rs.next()) {
                ordine=new Ordine();
                Item i;
                i = new Item(rs.getInt("ItemId"), rs.getString("Name"), rs.getDouble("Price"), rs.getString("ItemNote"));
                ordine.insertItem(i, rs.getInt("Quantity"));
                allOrders.setIdTavolo(rs.getInt("OrderId"));
                allOrders.addOrder(ordine);
            }
            st.close();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    public void loadOrderNew() {
        try {
            PreparedStatement st = con.prepareStatement(getOrdini);
            ResultSet rs = st.executeQuery();
            OrderService allOrders= OrderService.getInstance();
            Ordine ordine;
            while (rs.next()) {
                ordine=new Ordine();
                Item i;
                i = new Item(rs.getInt("ItemId"), rs.getString("Name"), rs.getDouble("Price"), rs.getString("ItemNote"));
                ordine.insertItem(i, rs.getInt("Quantity"));
                allOrders.setIdTavolo(rs.getInt("TableId"));
                allOrders.addOrder(ordine);


            }
            st = con.prepareStatement(getOrdiniPagati);
            rs = st.executeQuery();
            if (rs.next()) {
                allOrders.setTotalOrderPagati(rs.getInt(1));
            }

            st = con.prepareStatement(getOrdiniNonPagati);
            rs = st.executeQuery();
            if (rs.next()) {
                allOrders.setTotalOrderNonPagati(rs.getInt(1));
            }




            //System.out.println(allOrders);
            st.close();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }



    public void loadTable(){
        try{
            PreparedStatement st = con.prepareStatement(getTavoli);
            ResultSet rs = st.executeQuery();
            TableService tableService= TableService.getInstance();
            while(rs.next()){
                Table t = new Table(rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getInt(4), rs.getInt(5));
                tableService.addTable(t);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Item> getAllItems() {
        try {
            ArrayList<Item> items = new ArrayList<>();
            PreparedStatement st = con.prepareStatement(getItems);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Item i = new Item(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6));
                items.add(i);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Pair<Integer,String>> getAllCategories() {
        try {
            ArrayList<Pair<Integer,String>> categories = new ArrayList<>();
            PreparedStatement st = con.prepareStatement(getCategories);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Pair<Integer,String> c = new Pair<>(rs.getInt(1),rs.getString(2));
                categories.add(c);
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getIngredientsByItemId(int itemId) {
        try {
            ArrayList<String> ingredients = new ArrayList<>();
            PreparedStatement st = con.prepareStatement(getIngredientsByItemId);
            st.setInt(1,itemId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                ingredients.add(rs.getString(1));
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Flag> getFlags() {
        try {
            ArrayList<Flag> flags = new ArrayList<>();
            PreparedStatement st = con.prepareStatement(getFlags);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Flag f = new Flag(rs.getInt(1),rs.getString(2), rs.getString(3));
                flags.add(f);
            }
            return flags;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public int createOrder(Ordine ord) {
        try {
            PreparedStatement st = con.prepareStatement(createOrder, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1,ord.getIdTavolo());
            if (st.executeUpdate() > 0) {
                ResultSet rs = st.getGeneratedKeys();
                rs.next();
                int idOrdine = rs.getInt(1);
                ord.setIdOrdine(rs.getInt(1));
                for (int i = 0; i < ord.getListaOrdine().size(); i++) {
                    PreparedStatement stItem = con.prepareStatement(putItemsInOrder);
                    stItem.setInt(1,idOrdine);
                    stItem.setInt(2,ord.getListaOrdine().get(i).getValue().getId());
                    stItem.setInt(3,ord.getListaOrdine().get(i).getKey());
                    stItem.setString(4,ord.getListaOrdine().get(i).getValue().getNotes());
                    stItem.executeUpdate();
                }
                return idOrdine;
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
