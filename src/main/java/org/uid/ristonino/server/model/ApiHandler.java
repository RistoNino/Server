package org.uid.ristonino.server.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import javafx.util.Pair;
import org.uid.ristonino.server.model.services.ItemService;
import org.uid.ristonino.server.model.services.MenuService;
import org.uid.ristonino.server.model.services.OrderService;
import org.uid.ristonino.server.model.services.TableService;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.model.types.Ordine;
import org.uid.ristonino.server.model.types.Table;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;


public class ApiHandler extends AbstractVerticle {
    private final MenuService menuService = new MenuService();
    private final OrderService orderService = OrderService.getInstance();
    private final ItemService itemService = ItemService.getInstance();
    private TableService tableService = new TableService();

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/api/menu").handler(this::getAllItems);
        // /api/menu returns the current menu
        router.get("/api/images/:idItem").handler(this::getImage);
        // /api/images/{idItem} returns the image of the item
        // in case of a flag, put in the body
        router.post("/api/orders").handler(this::createOrder);
        // /api/orders places a new order
        router.post("/api/tables").handler(this::putTable);

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

    private void putTable(RoutingContext routingContext) {
        Table table;
        try {
            table = deserializeTable(routingContext.getBodyAsString());
        } catch (JsonProcessingException e) {
            routingContext.response()
                    .setStatusCode(400)
                    .end("Invalid request body: " + e.getMessage());
            return;
        }
        table.setIp(routingContext.request().remoteAddress().host());

        if (tableService.addNewTable(table)) {
            routingContext.response().setStatusCode(200).end("Table added");
        }
        else {
            routingContext.response().setStatusCode(500).end("Table couldn't be added, check out the server for more details");
        }
    }


    private void getAllItems(RoutingContext routingContext) {
        menuService.getAllItems().onSuccess(items -> {
            routingContext.response()
                    .putHeader("content-type", "application/json")
                    .end(io.vertx.core.json.Json.encodePrettily(items));
        });
    }



    private void getImage(RoutingContext routingContext) {
        String param = routingContext.request().getParam("file");
        String imagePath;

        if (Debug.IS_ACTIVE) {
            imagePath = Debug.IMAGE_PATH + "images/" + param;
        }
        else {
            Optional<Item> opt = itemService.getItems().stream()
                    .filter(item -> item.getId() == Integer.parseInt(param))
                    .findFirst();

            if (opt.isPresent()) {
                imagePath = opt.get().getPathImage();
            }
            else {
                routingContext.response().setStatusCode(404).end("Image not found");
            }
        }

        try(InputStream inputStream = ApiHandler.class.getResourceAsStream(imagePath)) {
            if (inputStream == null) {
                routingContext.response().setStatusCode(404).end("Image not found");
                return;
            }
            Buffer imageBuffer = Buffer.buffer(inputStream.readAllBytes());
            String contentType = getContentType(param);

            routingContext.response().putHeader("Content-Type", contentType);
            routingContext.response().putHeader("Content-Length", String.valueOf(imageBuffer.length()));
            routingContext.response().end(imageBuffer);
        } catch (IOException e) {
            routingContext.response().setStatusCode(500).end("Unable to load image");
        }
    }

    private void createOrder(RoutingContext routingContext) {
        String body = routingContext.getBodyAsString();
        Ordine order;
        try {
            System.out.println("body: "+body);
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

        JsonArray jsonArray = jsonObject.getJsonArray("items");
        System.out.println("Deserialize pre fors: "+jsonArray.size());

        for (int i = 0; i < jsonArray.size(); i++) {
            System.out.println("Deserialize");
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

    private Table deserializeTable(String json) throws JsonProcessingException {
        JsonObject j = new JsonObject(json);
        Table t = new Table(j.getInteger("idTable"), "", true, j.getInteger( "numberCovers"), j.getInteger("maxCovers"));
        return t;
    }


    private String getContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }

}
