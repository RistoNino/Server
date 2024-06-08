package org.uid.ristonino.server.model.types;

import javafx.util.Pair;
import org.uid.ristonino.server.model.DatabaseHandler;

import java.util.ArrayList;

public class Menu {
    private final ArrayList<Pair<Integer, String>> categories = new ArrayList<>();
    private final ArrayList<Flag> flags = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();

    private final DatabaseHandler db = DatabaseHandler.getInstance();

    public Menu() {
        categories.addAll(db.getAllCategories());
        flags.addAll(db.getFlags());
        items.addAll(db.getAllItems());
        for (int i = 0; i < items.size(); i++) {
            int id = items.get(i).getId();
            ArrayList<String> ingredients = db.getIngredientsByItemId(id);
            items.get(i).setIngredients(ingredients);
        }
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

    public ArrayList<Pair<Integer, String>> getCategories() {
        return categories;
    }

    public ArrayList<Item> getItems() {
        return items;
    }


}
