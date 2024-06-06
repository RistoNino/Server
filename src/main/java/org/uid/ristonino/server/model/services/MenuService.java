package org.uid.ristonino.server.model.services;

import io.vertx.core.Future;
import javafx.util.Pair;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Flag;
import org.uid.ristonino.server.model.types.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuService {
    private final ArrayList<Pair<Integer, String>> categories = new ArrayList<>();
    private final ArrayList<Flag> flags = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private final DatabaseHandler db = DatabaseHandler.getInstance();
    public MenuService() {
        categories.addAll(db.getAllCategories());
        // ricevi flags
        items.addAll(db.getAllItems());
        for (int i = 0; i < items.size(); i++) {
            int id = items.get(i).getId();
            ArrayList<String> ingredients = db.getIngredientsByItemId(id);
            items.get(i).setIngredients(ingredients);
        }
    }
    public Future<List<Item>> getAllItems() {
        return Future.succeededFuture(items);
    }
}
