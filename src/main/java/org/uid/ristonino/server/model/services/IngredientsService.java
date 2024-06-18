package org.uid.ristonino.server.model.services;

import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Ingrediente;

import java.util.ArrayList;

public class IngredientsService {
    private ArrayList<Ingrediente> i = new ArrayList<>();
    private final DatabaseHandler db = DatabaseHandler.getInstance();

    public IngredientsService() {
        i = db.getIngredients();
    }
    public ArrayList<Ingrediente> getIngredients() {
        return i;
    }
}
