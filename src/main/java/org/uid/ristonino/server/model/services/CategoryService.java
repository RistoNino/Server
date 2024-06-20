package org.uid.ristonino.server.model.services;

import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Categoria;
import org.uid.ristonino.server.model.types.Ingrediente;

import java.util.ArrayList;

public class CategoryService {
    private ArrayList<Categoria> i;
    private final DatabaseHandler db = DatabaseHandler.getInstance();

    private static CategoryService instance = new CategoryService();
    public static CategoryService getInstance() {return instance; }

    public CategoryService() {
        i = db.getAllCategories();
    }
    public ArrayList<Categoria> getCategories() {
        return i;
    }
    public boolean addCategory(Categoria c) {
        return i.add(c) && db.createCategory(c) >= 0;
    }

    public boolean removeCategory(Categoria selectedItem) {
        return i.remove(selectedItem) && db.removeCategory(selectedItem.getIdCategoria());
    }
}
