package org.uid.ristonino.server.model.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.Debug;
import org.uid.ristonino.server.model.ImageBase64Encoder;
import org.uid.ristonino.server.model.types.Menu;

import java.io.IOException;
import java.util.ArrayList;

public class MenuService {

    private final Menu m;
    private final DatabaseHandler db = DatabaseHandler.getInstance();
    private ImageBase64Encoder conv = new ImageBase64Encoder();

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
    public Future<JsonObject> getAllItems() {

        for (int i = 0; i < m.getItems().size(); i++) {
            String path = Debug.PATH + m.getItems().get(i).getPathImage();
            try {
                m.getItems().get(i).setBase64(conv.encodeImageToBase64(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


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
