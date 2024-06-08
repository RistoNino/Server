package org.uid.ristonino.server.controller;



import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.uid.ristonino.server.model.services.MenuService;

import java.awt.*;



public class ApiController extends AbstractVerticle {
    private final MenuService menuService = new MenuService();

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/api/menu").handler(this::getAllItems);
        // router.post("/api/orders/:id").handler(this::createOrder);

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



//    private void createOrder(RoutingContext routingContext) {
//        Ordine order = routingContext.getBodyAsJson().mapTo(Ordine.class);
//        OrderService.saveOrder(order).onSuccess(savedOrder -> {
//            routingContext.response()
//                    .putHeader("content-type", "application/json")
//                    .end(io.vertx.core.json.Json.encodePrettily(savedOrder));
//        });
//    }

}
