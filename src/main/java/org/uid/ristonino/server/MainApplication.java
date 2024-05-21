package org.uid.ristonino.server;

import javafx.application.Application;
import javafx.stage.Stage;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.view.SceneHandler;

public class MainApplication extends Application {
    DatabaseHandler db = DatabaseHandler.getInstance();
    @Override
    public void start(Stage primaryStage){
        SceneHandler.getInstance().init(primaryStage);
    }

    @Override
    public void init() throws Exception {
        super.init();
        db.openConnection();
    }

    @Override
    public void stop() {
        db.closeConnection();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
