package org.uid.ristonino.server;

import io.vertx.core.Vertx;
import javafx.application.Application;
import javafx.stage.Stage;
import org.uid.ristonino.server.model.ApiHandler;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.view.SceneHandler;

public class MainApplication extends Application {
    DatabaseHandler db = DatabaseHandler.getInstance();

    @Override
    public void start(Stage primaryStage) {
        SceneHandler.getInstance().init(primaryStage);
    }

    @Override
    public void init() throws Exception {
        super.init();
        db.openConnection();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ApiHandler());
    }

    @Override
    public void stop() {
        db.cleanOrdersAndTables();
        db.closeConnection();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
