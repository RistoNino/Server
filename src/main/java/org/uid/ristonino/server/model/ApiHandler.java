package org.uid.ristonino.server.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import javafx.util.Pair;
import org.uid.ristonino.server.model.services.MenuService;
import org.uid.ristonino.server.model.services.OrderService;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.model.types.Ordine;

import java.util.ArrayList;


public class ApiHandler extends AbstractVerticle {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MenuService menuService = new MenuService();
    private final OrderService orderService = OrderService.getInstance();

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/api/menu").handler(this::getAllItems);
        router.post("/api/orders").handler(this::createOrder);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, http -> {
                    if (http.succeeded()) {
                        startPromise.complete();
                    } else {
                        startPromise.fail(http.cause());
                    }
                });
    }

    private void getAllItems(RoutingContext routingContext) {
        menuService.getAllItems().onSuccess(items -> {
            routingContext.response()
                    .putHeader("content-type", "application/json")
                    .end(io.vertx.core.json.Json.encodePrettily(items));
        });
    }

    private void createOrder(RoutingContext routingContext) {
        String body = routingContext.getBodyAsString();
        Ordine order;
        try {
            order = deserializeOrder(body);
        }
        catch (JsonProcessingException e) {
            routingContext.response()
                    .setStatusCode(400)
                    .end("Invalid request body: " + e.getMessage());
            return;
        }
        orderService.saveOrder(order).onSuccess(savedOrder -> {
            routingContext.response()
                    .putHeader("content-type", "application/json")
                    .end(io.vertx.core.json.Json.encodePrettily(savedOrder));
        });
    }

    private Ordine deserializeOrder(String json) throws JsonProcessingException {
        JsonObject jsonObject = new JsonObject(json);
        ArrayList<Pair<Integer, Item>> items = new ArrayList<>();
        Ordine ordine = new Ordine();
        ordine.setIdTavolo(jsonObject.getInteger("idTavolo"));

//        {
//          idTavolo : y,
//          items : [
//              {
//                      id : x,
//                      quantity : z,
//                      notes : "note",
//                      removedIngredients : "NO ing1, ing2, ing3"
//              },
//              {
//                      id : xx,
//                      quantity : zz,
//                      notes : "note2",
//                      removedIngredients : "NO ing1, ing2, ing3"
//              }
//                  ]
//        }

        JsonArray jsonArray = jsonObject.getJsonArray("items");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject itemJson = jsonArray.getJsonObject(i);
            Integer idItem = itemJson.getInteger("id");
            Integer quantity = itemJson.getInteger("quantity");
            String notes = itemJson.getString("notes");
            String removedIngredients = itemJson.getString("removedIngredients");
            // Id, IdOrder, IdItem, Quantity, Notes
            Item item = new Item(idItem, notes, removedIngredients);
            Pair<Integer, Item> pair = new Pair<>(quantity, item);
            items.add(pair);
        }

        ordine.setListaOrdine(items);
        return ordine;
    }

}