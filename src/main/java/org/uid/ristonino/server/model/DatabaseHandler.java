//TODO: Sistemare i warning

package org.uid.ristonino.server.model;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.uid.ristonino.server.model.services.OrderService;
import org.uid.ristonino.server.model.services.TableService;
import org.uid.ristonino.server.model.types.*;
import org.uid.ristonino.server.view.SceneHandler;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseHandler {


    // lista query, da finire

    private final String createCategory = "INSERT INTO Categories (name) VALUES (?);";
    private final String createItem = "INSERT INTO Items (Name, Description, PathImage, Price, Category_Id) VALUES (?, ?, ?, ?, ?);";
    private final String addIngredientsToItem = "INSERT INTO Items_Ingredients (Item_Id, Ingredient_Id) VALUES (?, ?);";
    private final String addFlagsToItem = "INSERT INTO Items_Flags (Item_Id, Flag_Id) VALUES (?, ?);";
    private final String deleteItem = "DELETE FROM Items WHERE Id = ?;";
    private final String removeIngredientsFromItem = "DELETE FROM Items_Ingredients WHERE Item_Id = ?;";
    private final String removeFlagsFromItem = "DELETE FROM Items_Flags WHERE Item_Id = ?;";
    private final String createFlag = "INSERT INTO Flags (Name, PathImage) VALUES (?,?)";
    private final String deleteFlag = "DELETE FROM Flags WHERE Id = ?;";
    private final String createIngredient = "INSERT INTO Ingredients (name) VALUES (?);";
    private final String checkPasswordSt = "SELECT Password FROM Users WHERE Username = ?;";
    private final String createUser = "INSERT INTO Users (Username, Password, PrivilegesLevel) VALUES (?, ?, ?);";
    private final String checkUsername = "SELECT Username FROM Users WHERE Username = ?;";
    private final String getTavoli = "SELECT * FROM Tables";
    private final String getCategories = "SELECT * FROM Categories";
    private final String getItems = "SELECT * FROM Items";
    private final String getOrdineDaTavolo = "SELECT o.Id AS OrderId, i.id AS ItemId, Name, oi.Quantity, Price, oi.Note AS ItemNote FROM Items AS i, Orders_Items AS oi, Orders AS o, Tables AS t WHERE i.Id = oi.idItem AND oi.idOrder = o.Id AND o.idTable = t.Id;";
    private final String getIngredientsByItemId = "SELECT Name FROM Ingredients INNER JOIN Items_Ingredients on Ingredients.Id = Items_Ingredients.Ingredient_Id WHERE Items_Ingredients.Item_Id = ?;";
    private final String getFlags = "SELECT * FROM Flags;";
    private final String putItemsInOrder = "INSERT INTO Orders_Items (IdOrder, IdItem, Quantity, Note) VALUES (?,?,?,?)";
    private final String getOrdini="SELECT o.Id AS OrderId, i.Id AS ItemId, i.Name, oi.Quantity, i.Price, oi.Note AS ItemNote, t.Id AS TableId FROM Items AS i JOIN Orders_Items AS oi ON i.Id = oi.IdItem JOIN Orders AS o ON oi.IdOrder = o.Id JOIN Tables AS t ON o.IdTable = t.Id;";
    //private final String createOrder = "INSERT INTO Orders (IdTable, Note) VALUES (?,?);";
    private final String getOrdiniPagati = "SELECT COUNT(*) FROM Orders WHERE Pagato = 1;";
    private final String getOrdiniNonPagati = "SELECT COUNT(*) FROM Orders WHERE Pagato = 0;";
    private final String getPagatiONo ="SELECT IdTable, Pagato FROM Orders;";
    private final String removeOrders_Item="DELETE FROM Orders_Items WHERE IdOrder IN (SELECT Id FROM Orders WHERE IdTable = ?);";
    private final String updatePayState="UPDATE Orders SET Pagato = ? WHERE IdTable = ?";
    private final String getIngredients = "SELECT * FROM Ingredients;";
    private final String removeCategory = "DELETE FROM Categories WHERE Id = ?;";
    private final String removeIngredient = "DELETE FROM Ingredients WHERE Id = ?;";
    private final String deleteAllTables = "DELETE FROM Tables";
    private final String deleteAllOrders = "DELETE FROM Orders";
    private final String addTable = "INSERT INTO Tables (Id, IpTable, isOccupied, copertiOccupati, maxCoperti) VALUES (?,?,?,?,?);";
    private final String checkTableExists = "SELECT COUNT(*) FROM Tables WHERE Id = ?;";
    private final String addOrder="INSERT INTO Orders (IdTable, Pagato) VALUES (?, ?);";


    OrderService allOrders;
    private final String url = "jdbc:sqlite:RistoNino.db";
    Connection con;


    private static DatabaseHandler instance = new DatabaseHandler();
    private DatabaseHandler() {}
    public static DatabaseHandler getInstance() { return instance; }

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

            if(rs.next()) {
                rs.close();
                st.close();
                return true;
            }
            else{
                rs.close();
                st.close();
                return false;
            }
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
                if(st.executeUpdate()>0) {
                    st.close();
                    return true;
                }else{
                    st.close();
                    return false;
                }
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
                rs.close();
                st.close();
                return BCrypt.checkpw(passwordInput,hashedPassword);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void cleanOrdersAndTables(){
        try{
            Statement stmt = con.createStatement();
            int x = stmt.executeUpdate(deleteAllTables);
            stmt.close();
            stmt.executeQuery(deleteAllOrders);
            stmt.close();
            // System.out.println("***!!*: "+x);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }



    public void loadOrder() {
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
            st.close();
            rs.close();

            st = con.prepareStatement(getOrdiniPagati);
            rs = st.executeQuery();
            if (rs.next()) {
                allOrders.setTotalOrderPagati(rs.getInt(1));
            }
            st.close();
            rs.close();
            st = con.prepareStatement(getOrdiniNonPagati);
            rs = st.executeQuery();
            if (rs.next()) {
                allOrders.setTotalOrderNonPagati(rs.getInt(1));
            }
            st.close();
            rs.close();
            st = con.prepareStatement(getPagatiONo);
            rs = st.executeQuery();
            while(rs.next()) {
                // System.out.println("rs.getInt(\"IdTable\"), rs.getBoolean(\"Pagato\")"+rs.getInt("IdTable")+rs.getBoolean("Pagato"));
                allOrders.insertOrderState(rs.getInt("IdTable"), rs.getBoolean("Pagato"));
            }
            //System.out.println(allOrders);
            st.close();
            rs.close();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }



    public void removeOrderByTableId(int id){
        try {
            PreparedStatement deleteOrdersItemsStmt = con.prepareStatement(removeOrders_Item);
            //PreparedStatement deleteOrdersStmt = con.prepareStatement(removeOrders);
            deleteOrdersItemsStmt.setInt(1, id);
            deleteOrdersItemsStmt.executeUpdate();
            // System.out.println("Ordine rimosso con successo per il tavolo con ID: " + id);
            deleteOrdersItemsStmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadTable(){
        System.out.println("loadTable in DatabaseHandler");
        this.closeConnection();
        this.openConnection();

        try{
            PreparedStatement st = con.prepareStatement(getTavoli);
            ResultSet rs = st.executeQuery();
            TableService tableService= TableService.getInstance();
            while(rs.next()){
                System.out.println("loadTable in DatabaseHandler: "+rs.getString(2));
                Table t = new Table(rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getInt(4), rs.getInt(5));
                tableService.addNewTable(t);
            }
            rs.close();
            st.close();
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
            rs.close();
            st.close();
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Categoria> getAllCategories() {
        try {
            ArrayList<Categoria> categories = new ArrayList<>();
            PreparedStatement st = con.prepareStatement(getCategories);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Categoria c = new Categoria(rs.getString(2),rs.getInt(1));
                categories.add(c);
            }
            rs.close();
            st.close();
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
            rs.close();
            st.close();
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
            rs.close();
            st.close();
            return flags;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStateById(int id, int state){
        try {
            // System.out.println("QuaaaStateId");
            PreparedStatement preparedStatement= con.prepareStatement(updatePayState);
            preparedStatement.setInt(1, state);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private final String getIdOrder="SELECT Id FROM Orders WHERE IdTable = ?;";

    public int createOrder(Ordine ord) {
        try {
            PreparedStatement st = con.prepareStatement(getIdOrder);
            st.setInt(1,ord.getIdTavolo());
            ResultSet rs=st.executeQuery();
            int id=0;
            if(rs.next())
                id=rs.getInt("Id");

            System.out.println("Id: "+id);

            if (id > 0) {
                ord.setIdOrdine(id);
                System.out.println("Order: "+ord.getListaOrdine());
                for (int i = 0; i < ord.getListaOrdine().size(); i++) {
                    System.out.println("a");
                    PreparedStatement stItem = con.prepareStatement(putItemsInOrder);
                    stItem.setInt(1,id);
                    stItem.setInt(2,ord.getListaOrdine().get(i).getValue().getId());
                    stItem.setInt(3,ord.getListaOrdine().get(i).getKey());
                    stItem.setString(4,ord.getListaOrdine().get(i).getValue().getNotes());
                    stItem.executeUpdate();
                }
                rs.close();
                st.close();
                return id;
            }
            st.close();
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createCategory(Categoria c) {
        try {
            PreparedStatement st = con.prepareStatement(createCategory, Statement.RETURN_GENERATED_KEYS);
            st.setString(1,c.getName());
            if (st.executeUpdate() > 0) {
                ResultSet rs = st.getGeneratedKeys();
                rs.next();
                int idCategory = rs.getInt(1);
                c.setId(idCategory);
                rs.close();
                st.close();
                return idCategory;
            }
            st.close();

            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createFlag(Flag f) {
        try {
            PreparedStatement st = con.prepareStatement(createFlag, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, f.getName());
            st.setString(2, f.getPathImage());
            if (st.executeUpdate() > 0) {
                ResultSet rs = st.getGeneratedKeys();
                rs.next();
                int idFlag = rs.getInt(1);
                f.setId(idFlag);
                rs.close();
                return idFlag;
            }
            st.close();
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Ingrediente> getIngredients() {
        try {
            ArrayList<Ingrediente> ingredients = new ArrayList<>();
            PreparedStatement st = con.prepareStatement(getIngredients);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Ingrediente i = new Ingrediente(rs.getInt(1), rs.getString(2));
                ingredients.add(i);
            }
            rs.close();
            st.close();
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createIngredient(Ingrediente ing) {
        try {
            PreparedStatement st = con.prepareStatement(createIngredient, Statement.RETURN_GENERATED_KEYS);
            st.setString(1,ing.getName());
            if (st.executeUpdate() > 0) {
                ResultSet rs = st.getGeneratedKeys();
                rs.next();
                int idIngredient = rs.getInt(1);
                ing.setId(idIngredient);
                st.close();
                rs.close();
                return idIngredient;
            }
            st.close();
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createItem(Item item) {
        PreparedStatement createItemStatement = null;
        PreparedStatement addIngredientsStatement = null;
        PreparedStatement addFlagsStatement = null;
        ResultSet rs = null;

        try {
            con.setAutoCommit(false);
            // Name, Description, PathImage, Price, Category_Id
            createItemStatement = con.prepareStatement(createItem, Statement.RETURN_GENERATED_KEYS);
            createItemStatement.setString(1, item.getName());
            createItemStatement.setString(2, item.getDescription());
            createItemStatement.setString(3, item.getPathImage());
            createItemStatement.setDouble(4, item.getPrice());
            createItemStatement.setInt(5, item.getCategory()); // TODO: sostiture con get dell'oggetto categoria e get id

            if (createItemStatement.executeUpdate() > 0) {
                rs = createItemStatement.getGeneratedKeys();
                rs.next();
                int idItem = rs.getInt(1);
                item.setId(idItem);

                addIngredientsStatement = con.prepareStatement(addIngredientsToItem);
                ArrayList<Ingrediente> ingredients = item.getIngredientes();
                // aggiungo gli ingredienti alla tabella n:m
                for (int i = 0; i < ingredients.size(); i++) {
                    addIngredientsStatement.setInt(1, idItem);
                    addIngredientsStatement.setInt(2, ingredients.get(i).getId());
                    addIngredientsStatement.addBatch();
                }
                addIngredientsStatement.executeBatch();


                addFlagsStatement = con.prepareStatement(addFlagsToItem);
                ArrayList<Flag> flags = item.getFlags();
                // aggiungo le flags
                for (int i = 0; i < flags.size(); i++) {
                    addFlagsStatement.setInt(1, idItem);
                    addFlagsStatement.setInt(2, flags.get(i).getId());
                    addFlagsStatement.addBatch();
                }
                addFlagsStatement.executeBatch();

                con.commit();
                rs.close();
                addIngredientsStatement.close();
                addFlagsStatement.close();
                createItemStatement.close();
                return true;
            }
        } catch (SQLException e) {
            try {
                // Rollback in caso di errore
                con.rollback();
            } catch (SQLException rollback) {
                rollback.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (createItemStatement != null) createItemStatement.close();
                if (addIngredientsStatement != null) addIngredientsStatement.close();
                if (addFlagsStatement != null) addFlagsStatement.close();
                con.setAutoCommit(true); // Ripristina l'auto-commit
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
                throw new RuntimeException(closeEx);
            }
        }
        return false;
    }

    public boolean removeCategory(int id) {
        try {
            PreparedStatement st = con.prepareStatement(removeCategory);
            st.setInt(1,id);
            int rows = st.executeUpdate();
            st.close();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeIngredient(int id) {
        try {
            PreparedStatement st = con.prepareStatement(removeIngredient);
            st.setInt(1,id);
            int rows = st.executeUpdate();
            st.close();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteItem(Item item) {
        try {
            PreparedStatement deleteItemStatement = con.prepareStatement(deleteItem, Statement.RETURN_GENERATED_KEYS);
            deleteItemStatement.setInt(1, item.getId());
            if (deleteItemStatement.executeUpdate() > 0) {
                deleteItemStatement.close();
                return true;
            }
            deleteItemStatement.close();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeFlag(Flag f) {
        try {
            PreparedStatement deleteItemStatement = con.prepareStatement(deleteFlag, Statement.RETURN_GENERATED_KEYS);
            deleteItemStatement.setInt(1, f.getId());
            if (deleteItemStatement.executeUpdate() > 0) {
                deleteItemStatement.close();
                return true;
            }
            deleteItemStatement.close();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkTableExists(Table t) {
        try {
            PreparedStatement checkTableStatement = con.prepareStatement(checkTableExists);
            checkTableStatement.setInt(1, t.getId());
            if (checkTableStatement.execute()) {
                ResultSet rs = checkTableStatement.getResultSet();
                if(rs.getInt(1)>0){
                    checkTableStatement.close();
                    rs.close();
                    return true;
                }else {
                    checkTableStatement.close();
                    rs.close();
                    return false;
                }
            }
            checkTableStatement.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean addTable(Table table) {
        try {
            if (!checkTableExists(table)) {
                PreparedStatement addTableStatement = con.prepareStatement(addTable);
                addTableStatement.setInt(1, table.getId());
                addTableStatement.setString(2, table.getIp());
                addTableStatement.setBoolean(3, table.isIsoccupied());
                addTableStatement.setInt(4, table.getOccupied());
                addTableStatement.setInt(5, table.getMaxOccupied());

                //Ogni volta che aggiungo il tavolo, devo aggiungere l'ordine nella tabella Orders.

                PreparedStatement pstmt = con.prepareStatement(addOrder);
                pstmt.setInt(1, table.getId());
                pstmt.setInt(2, 0);

                pstmt.executeUpdate();
                pstmt.close();
                if (addTableStatement.executeUpdate() > 0) {
                    addTableStatement.close();
                    return true;
                }
                addTableStatement.close();




            }
            else {
                System.out.println("table with id:" + table.getId() + " already exists");
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
