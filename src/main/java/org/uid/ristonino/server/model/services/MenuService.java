package org.uid.ristonino.server.model.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.Debug;
import org.uid.ristonino.server.model.types.Menu;

import java.util.ArrayList;

public class MenuService {

    private final Menu m;
    private final DatabaseHandler db = DatabaseHandler.getInstance();


    public MenuService() {
        m = new Menu();
        m.setCategories(db.getAllCategories());
        m.setFlags(db.getFlags());
        m.setItems(db.getAllItems());
        for (int i = 0; i < m.getItems().size(); i++) {
            int id = m.getItems().get(i).getId();
            ArrayList<String> ingredients = db.getIngredientsByItemId(id);
            m.getItems().get(i).setIngredients(ingredients);
        }
    }

    public Menu getMenu() {
        return m;
    }

    public Future<JsonObject> getAllItems() {


//        for (int i = 0; i < m.getFlags().size(); i++) {
//            String path = Debug.IMAGE_PATH + m.getFlags().get(i).getPathImage();
//            //System.out.println("Path Flag: " + path);
//
//        }
//
//        for (int i = 0; i < m.getItems().size(); i++) {
//            String path = Debug.PATH + m.getItems().get(i).getPathImage();
//            System.out.println("Path Items: " + path);
//
//        }


        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(m);
            JsonObject j = new JsonObject(jsonString);
            return Future.succeededFuture(j);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
