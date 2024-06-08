package org.uid.ristonino.server.model.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import javafx.util.Pair;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Flag;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.model.types.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuService {

    private final Menu m;
    public MenuService() {
        m = new Menu();
    }
    public Future<JsonObject> getAllItems() {
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
