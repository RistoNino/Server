package org.uid.ristonino.server.model.services;

import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Ingrediente;
import org.uid.ristonino.server.view.SceneHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientsService {
    private ArrayList<Ingrediente> i;
    private final DatabaseHandler db = DatabaseHandler.getInstance();

    private static final IngredientsService instance = new IngredientsService();
    public static IngredientsService getInstance() {
        return instance;
    }
    public IngredientsService() {
        i = db.getIngredients();
    }
    public ArrayList<Ingrediente> getIngredients() {
        return i;
    }

    public boolean addIngredient(Ingrediente ing) {
        return i.add(ing) && db.createIngredient(ing) >= 0;
    }
    public void removeIngredient(Ingrediente ing) {
         i.remove(ing);
         db.removeIngredient(ing.getId());
    }
    public static List<Ingrediente> getSelectedIngredients(List<Ingrediente> flags) {
        return flags.stream()
                .filter(Ingrediente::isSelected)
                .collect(Collectors.toList());
    }
}
