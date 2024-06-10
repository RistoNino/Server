package org.uid.ristonino.server.model.types;

import javafx.util.Pair;
import org.uid.ristonino.server.model.DatabaseHandler;

import java.util.ArrayList;

public class Menu {


    private ArrayList<Pair<Integer, String>> categories = new ArrayList<>();
    private ArrayList<Flag> flags = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();


    public Menu() {

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

    public void setCategories(ArrayList<Pair<Integer, String>> categories) {
        this.categories = categories;
    }

    public void setFlags(ArrayList<Flag> flags) {
        this.flags = flags;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
