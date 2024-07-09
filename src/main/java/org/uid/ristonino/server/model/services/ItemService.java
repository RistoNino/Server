package org.uid.ristonino.server.model.services;

import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Item;

import java.util.ArrayList;

public class ItemService {
    private static ItemService instance = new ItemService();
    public ItemService() { instance = this; }
    public static ItemService getInstance() { return instance; }
    private final DatabaseHandler db = DatabaseHandler.getInstance();

    private ArrayList<Item> items = db.getAllItems();


    public boolean createItem(Item item) {
         return items.add(item) && db.createItem(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item) && db.deleteItem(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
