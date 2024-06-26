package org.uid.ristonino.server.model.services;

import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Item;

public class ItemService {
    private static ItemService instance = new ItemService();
    public ItemService() { instance = this; }
    public static ItemService getInstance() { return instance; }

    private final DatabaseHandler db = DatabaseHandler.getInstance();

    public boolean createItem(Item item) {
         return db.createItem(item);
    }

    public boolean removeItem(Item item) {
        return db.deleteItem(item);
    }
}
